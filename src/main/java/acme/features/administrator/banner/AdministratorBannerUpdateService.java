
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface -------------------------------------


	@Override
	public void authorise() {
		Boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationUpdateMoment", "displayPeriodInitial", "displayPeriodFinal", "picture", "slogan", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodInitial") && object.getInstantiationUpdateMoment() != null)
			super.state(MomentHelper.isAfter(object.getDisplayPeriodInitial(), object.getInstantiationUpdateMoment()), "displayPeriodInitial", "administrator.banner.form.error.invalidPeriodInitial");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodFinal") && object.getDisplayPeriodInitial() != null) {
			Date minimumDisplayPeriodFinal;

			minimumDisplayPeriodFinal = MomentHelper.deltaFromMoment(object.getDisplayPeriodInitial(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodFinal(), minimumDisplayPeriodFinal), "displayPeriodFinal", "administrator.banner.form.error.too-close");

		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationUpdateMoment", "displayPeriodInitial", "displayPeriodFinal", "picture", "slogan", "link");

		super.getResponse().addData(dataset);
	}

}

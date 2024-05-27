
package acme.features.any.claim;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class AnyClaimCreateService extends AbstractService<Any, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyClaimRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Any.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Claim object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Claim();
		object.setInstantiationMoment(moment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Claim existing;

			existing = this.repository.findOneClaimByCode(object.getCode());
			super.state(existing == null, "code", "administrator.claim.form.error.duplicated");

		}
		if (!super.getBuffer().getErrors().hasErrors("link") && object.getLink() != null)
			super.state(object.getLink().length() >= 7 && object.getLink().length() <= 255 || object.getLink().length() == 0, "link", "any.claim.form.error.link");

		if (!super.getBuffer().getErrors().hasErrors("emailAddress") && object.getEmailAddress() != null)
			super.state(object.getEmailAddress().length() >= 6 && object.getEmailAddress().length() <= 254 || object.getEmailAddress().length() == 0, "emailAddress", "any.claim.form.error.emailAddress");

	}

	@Override
	public void perform(final Claim object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");

		super.getResponse().addData(dataset);
	}

}

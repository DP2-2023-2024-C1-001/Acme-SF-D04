
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipListService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Sponsorship> objects;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();

		objects = this.repository.findAllSponsorshipsBySponsorId(id);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;
		long duration;

		long res = object.getFinalDate().getTime() - object.getInitialDate().getTime();
		duration = res / (1000 * 60 * 60 * 24); // duracion en numero de dias

		dataset = super.unbind(object, "code", "moment", "type");
		dataset.put("duration", duration);

		super.getResponse().addData(dataset);
	}
}

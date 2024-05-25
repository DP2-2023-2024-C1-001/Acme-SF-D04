
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.invoice.Invoice;
import acme.entities.project.Project;
import acme.entities.sponsorship.SponsorType;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		masterId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findSponsorshipById(masterId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && !sponsorship.isPublished() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findProjectById(projectId);

		super.bind(object, "code", "moment", "initialDate", "finalDate", "amount", "type", "email", "link");
		object.setProject(project);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("amount") && object.getProject() != null) {

			Double amount;
			amount = object.getAmount().getAmount();
			super.state(amount >= 0, "amount", "sponsor.sponsorship.form.error.negativeAmount");

			super.state(object.getAmount().getCurrency().equals(object.getProject().getCost().getCurrency()), "amount", "sponsor.sponsorship.form.error.currency");

		}

		int sponsorshipId;
		double sumOfInvoicesTotalAmount = 0.00;
		sponsorshipId = object.getId();
		Collection<Invoice> invoicesForSponsorship;
		invoicesForSponsorship = this.repository.findAllPublisedInvoicesBySponsorShipsId(sponsorshipId);

		for (Invoice i : invoicesForSponsorship)
			sumOfInvoicesTotalAmount += i.totalAmount().getAmount();

		super.state(object.getAmount().getAmount() == sumOfInvoicesTotalAmount, "*", "sponsor.sponsorship.form.error.amount");

		if (!super.getBuffer().getErrors().hasErrors("initialDate") && object.getMoment() != null)
			super.state(MomentHelper.isBefore(object.getMoment(), object.getInitialDate()), "initialDate", "sponsor.sponsorship.form.error.date-before-moment");

		if (!super.getBuffer().getErrors().hasErrors("finalDate") && object.getInitialDate() != null) {
			Date minimumPeriod;

			minimumPeriod = MomentHelper.deltaFromMoment(object.getInitialDate(), 1, ChronoUnit.MONTHS);
			super.state(MomentHelper.isAfterOrEqual(object.getFinalDate(), minimumPeriod), "finalDate", "sponsor.sponsorship.form.error.too-close-date");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "sponsor.sponsorship.form.error.duplicated");

		}

		if (!super.getBuffer().getErrors().hasErrors("link") && object.getLink() != null)
			super.state(object.getLink().length() >= 7 && object.getLink().length() <= 255 || object.getLink().length() == 0, "link", "sponsor.sponsorship.form.error.link");

		if (!super.getBuffer().getErrors().hasErrors("email") && object.getEmail() != null)
			super.state(object.getEmail().length() >= 6 && object.getEmail().length() <= 254 || object.getEmail().length() == 0, "email", "sponsor.sponsorship.form.error.email");

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		object.setPublished(true);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices typesChoices;
		Dataset dataset;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());
		typesChoices = SelectChoices.from(SponsorType.class, object.getType());

		dataset = super.unbind(object, "code", "moment", "initialDate", "finalDate", "amount", "email", "link", "published");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("types", typesChoices);

		super.getResponse().addData(dataset);
	}

}

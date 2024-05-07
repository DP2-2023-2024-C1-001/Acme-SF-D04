
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findSponsorshipById(masterId);
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int masterId;
		Sponsorship sponsorship;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findSponsorshipById(masterId);

		object = new Invoice();
		object.setSponsorship(sponsorship);
		object.setPublished(false);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findInvoiceByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.invoice.form.error.duplicated");

		}

		if (!super.getBuffer().getErrors().hasErrors("dueDate") && object.getDueDate() != null && object.getRegistrationTime() != null) {
			Date minimumPeriod;

			minimumPeriod = MomentHelper.deltaFromMoment(object.getRegistrationTime(), 1, ChronoUnit.MONTHS);
			super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), minimumPeriod), "dueDate", "sponsor.invoice.form.error.too-close");

		}

		if (!super.getBuffer().getErrors().hasErrors("quantity") && object.getSponsorship() != null) {
			Double quantity;
			quantity = object.getQuantity().getAmount();
			super.state(quantity > 0, "quantity", "sponsor.invoice.form.error.negativeQuantity");

			super.state(object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.currency");

			int sponsorshipId = object.getSponsorship().getId();

			// Obtener el valor total del patrocinio
			double sponsorshipAmount = object.getSponsorship().getAmount().getAmount();

			// Obtener todas las facturas publicadas para este patrocinio
			Collection<Invoice> invoicesForSponsorship = this.repository.findAllPublisedInvoicesBySponsorShipsId(sponsorshipId);

			// Calcular el valor total de todas las facturas existentes, incluyendo la nueva factura
			double sumOfInvoicesTotalAmount = 0.0;
			for (Invoice invoice : invoicesForSponsorship)
				sumOfInvoicesTotalAmount += invoice.totalAmount().getAmount();

			// Añadir el valor de la nueva factura a la suma total
			sumOfInvoicesTotalAmount += object.totalAmount().getAmount();

			// Comprobar si la suma de las facturas, incluida la nueva factura, excede el valor del patrocinio
			super.state(sponsorshipAmount >= sumOfInvoicesTotalAmount, "quantity", "sponsor.invoice.form.error.sponsorship-amount");

		}

		if (!super.getBuffer().getErrors().hasErrors("tax")) {
			Double tax;

			tax = object.getTax();
			super.state(100 >= tax && tax >= 0, "tax", "sponsor.invoice.form.error.invalid-tax");

		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "published");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}

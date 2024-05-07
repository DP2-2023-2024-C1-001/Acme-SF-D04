
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.systemconfiguration.SystemConfiguration;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.sponsorship.id =:sponsorshipId")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int sponsorshipId);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId AND i.published = true")
	Collection<Invoice> findAllPublisedInvoicesBySponsorShipsId(int sponsorshipId);

	@Query("select i from Invoice i where i.id =:invoiceId")
	Invoice findInvoiceById(int invoiceId);

	@Query("select s from Sponsorship s where s.id =:sponsorshipId")
	Sponsorship findSponsorshipById(int sponsorshipId);

	@Query("select i.sponsorship from Invoice i where i.id= :id")
	Sponsorship findSponsorshipByInvoiceId(int id);

	@Query("select i from Invoice i where i.code = :code")
	Invoice findInvoiceByCode(String code);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

}

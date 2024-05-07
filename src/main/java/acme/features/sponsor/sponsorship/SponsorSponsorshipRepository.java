
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.systemconfiguration.SystemConfiguration;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.sponsor.id =:sponsorId")
	Collection<Sponsorship> findAllSponsorshipsBySponsorId(int sponsorId);

	@Query("select s from Sponsorship s where s.id =:sponsorshipId")
	Sponsorship findSponsorshipById(int sponsorshipId);

	@Query("select s.project from Sponsorship s where s.id =:sponsorshipId ")
	Collection<Project> findProjectBySponsorshipId(int sponsorshipId);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findSponsorById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findManyInvoicesBySponsorShipId(int id);

	@Query("select s from Sponsorship s where s.code= :code")
	Sponsorship findOneSponsorshipByCode(String code);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId AND i.published = true")
	Collection<Invoice> findAllPublisedInvoicesBySponsorShipsId(int sponsorshipId);
}

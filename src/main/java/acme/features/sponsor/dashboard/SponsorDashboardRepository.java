
package acme.features.sponsor.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(i) from Invoice i where i.sponsorship.sponsor.id = :sponsorId AND i.tax <= 21.00")
	Integer totalNumberOfInvoicesWithTaxLessOrEquals21Percent(int sponsorId);

	@Query("select count(s) from Sponsorship s where s.sponsor.id = :sponsorId AND s.link is not null and s.link not like ''")
	Integer totalNumberOfSponsorshipWithLink(int sponsorId);

	@Query("select s.amount.currency, avg(s.amount.amount) from Sponsorship s where s.sponsor.id = :sponsorId GROUP BY s.amount.currency")
	Collection<Object[]> averageAmountOfSponsorship(int sponsorId);

	@Query("select s.amount.currency, stddev(s.amount.amount) from Sponsorship s where s.sponsor.id = :sponsorId GROUP BY s.amount.currency")
	Collection<Object[]> deviationAmountOfSponsorship(int sponsorId);

	@Query("select s.amount.currency, min(s.amount.amount) from Sponsorship s where s.sponsor.id = :sponsorId GROUP BY s.amount.currency")
	Collection<Object[]> minimumAmountOfSponsorship(int sponsorId);

	@Query("select s.amount.currency, max(s.amount.amount) from Sponsorship s where s.sponsor.id = :sponsorId GROUP BY s.amount.currency")
	Collection<Object[]> maximumAmountOfSponsorship(int sponsorId);

	@Query("select i.quantity.currency, avg(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId GROUP BY i.quantity.currency")
	Collection<Object[]> averageQuantityOfInvoice(int sponsorId);

	@Query("select i.quantity.currency, stddev(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId GROUP BY i.quantity.currency")
	Collection<Object[]> deviationQuantityOfInvoice(int sponsorId);

	@Query("select i.quantity.currency, min(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId GROUP BY i.quantity.currency")
	Collection<Object[]> minimumQuantityOfInvoice(int sponsorId);

	@Query("select i.quantity.currency, max(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId GROUP BY i.quantity.currency")
	Collection<Object[]> maximumQuantityOfInvoice(int sponsorId);

	@Query("select sc.systemCurrency from SystemConfiguration sc")
	String getActualSystemConfiguration();

	default Map<String, Double> convertToMap(final Collection<Object[]> objectSet) {
		Map<String, Double> res = new HashMap<>();
		for (Object[] keyValuePair : objectSet) {
			String currency = keyValuePair[0].toString();
			Double statistic = (Double) keyValuePair[1];
			res.put(currency, statistic);
		}
		return res;
	}

}

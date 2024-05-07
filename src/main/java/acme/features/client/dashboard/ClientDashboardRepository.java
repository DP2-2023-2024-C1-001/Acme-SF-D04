
package acme.features.client.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId AND p.completeness < 25.00")
	Integer countOfProgressLogsWithCompletenessRateBelow25Percent(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId AND p.completeness < 50.00 AND p.completeness >= 25.00")
	Integer countOfProgressLogsWithCompletenessRateBetween25And50Percent(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId AND p.completeness <= 75.00 AND p.completeness >= 50.00")
	Integer countOfProgressLogsWithCompletenessRateBetween50And75Percent(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId AND p.completeness > 75.00")
	Integer countOfProgressLogsWithCompletenessRateAbove75Percent(int clientId);

	@Query("select c.budget.currency, avg(c.budget.amount) from Contract c where c.client.id = :clientId GROUP BY c.budget.currency")
	Collection<Object[]> avgBudgetOfContracts(int clientId);

	@Query("select c.budget.currency, stddev(c.budget.amount) from Contract c where c.client.id = :clientId GROUP BY c.budget.currency")
	Collection<Object[]> stddevBudgetOfContracts(int clientId);

	@Query("select c.budget.currency, min(c.budget.amount) from Contract c where c.client.id = :clientId GROUP BY c.budget.currency")
	Collection<Object[]> minBudgetOfContracts(int clientId);

	@Query("select c.budget.currency, max(c.budget.amount) from Contract c where c.client.id = :clientId GROUP BY c.budget.currency")
	Collection<Object[]> maxBudgetOfContracts(int clientId);

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

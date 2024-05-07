
package acme.features.administrator.systemconfiguration;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.systemconfiguration.Currency;
import acme.entities.systemconfiguration.SystemConfiguration;

@Repository
public interface AdministratorSystemConfigurationRepository extends AbstractRepository {

	@Query("select sc from SystemConfiguration sc where sc.id = :id")
	SystemConfiguration findOneSystemConfigurationById(int id);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

	@Query("select sc.systemCurrency  from SystemConfiguration sc")
	Collection<Currency> findCurrencies();

}

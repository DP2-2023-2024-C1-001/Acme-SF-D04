
package acme.features.administrator.risk;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.risk.Risk;

@Repository
public interface AdministratorRiskRepository extends AbstractRepository {

	@Query("select r from Risk r")
	Collection<Risk> findAllRisks();

	@Query("select r from Risk r where r.id = :id")
	Risk findOneRiskById(int id);

	@Query("select r from Risk r where r.reference= :reference")
	Risk findOneRiskByReference(String reference);

}

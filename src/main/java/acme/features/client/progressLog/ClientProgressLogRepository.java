
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progresslog.ProgressLog;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select p from ProgressLog p where p.contract.id = :contractId")
	Collection<ProgressLog> findAllProgressLogByContractId(int contractId);

	@Query("select p from ProgressLog p where p.id = :id")
	ProgressLog findOneProgressLogById(int id);

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select p.contract from ProgressLog p where p.id = :id")
	Contract findOneContractByProgressLogId(int id);

	@Query("select p from ProgressLog p where p.code = :code")
	ProgressLog findOneProgressLogByCode(String code);

	@Query("select sum(p.completeness) from ProgressLog p where p.contract.id = :contractId AND p.published = true")
	Double findActualCompletenessForAContract(int contractId);
}

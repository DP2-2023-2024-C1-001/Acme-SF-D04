
package acme.features.any.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progresslog.ProgressLog;

@Repository
public interface AnyProgressLogRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select p from ProgressLog p where p.id = :id")
	ProgressLog findOneProgressLogById(int id);

	@Query("select p from ProgressLog p where p.contract.id = :contractId AND p.published = true")
	Collection<ProgressLog> findAllPublishedProgressLogByContractId(int contractId);

	@Query("select p.contract from ProgressLog p where p.id = :id")
	Contract findOneContractByProgressLogId(int id);
}

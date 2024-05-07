
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progresslog.ProgressLog;
import acme.entities.project.Project;
import acme.entities.systemconfiguration.SystemConfiguration;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.client.id = :clientId")
	Collection<Contract> findAllContractsByClientId(int clientId);

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select c.project from Contract c where c.id = :id ")
	Collection<Project> findProjectByContractId(int id);

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select pl from ProgressLog pl where pl.contract.id = :id")
	Collection<ProgressLog> findManyProgressLogsByContractId(int id);

	@Query("select c from Contract c where c.code= :code")
	Contract findOneContractByCode(String code);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

	@Query("select c from Contract c where c.project.id = :projectId AND c.published = true")
	Collection<Contract> findAllContractsByProjectId(int projectId);
}

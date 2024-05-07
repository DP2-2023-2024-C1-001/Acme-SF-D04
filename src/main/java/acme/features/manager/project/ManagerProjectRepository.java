
package acme.features.manager.project;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditrecord.AuditRecord;
import acme.entities.codeaudit.CodeAudit;
import acme.entities.contract.Contract;
import acme.entities.invoice.Invoice;
import acme.entities.progresslog.ProgressLog;
import acme.entities.project.Project;
import acme.entities.project.UserStoryProject;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.systemconfiguration.SystemConfiguration;
import acme.entities.trainingmodule.TrainingModule;
import acme.entities.trainingsession.TrainingSession;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findAllProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select p from Project p where p.code = :code")
	Optional<Project> findOneProjectByCode(String code);

	@Query("select up from UserStoryProject up where up.project.id = :projectId")
	Collection<UserStoryProject> findUserStoryProjectsByProjectId(int projectId);

	@Query("select up.userStory from UserStoryProject up where up.project.id = :projectId")
	Collection<UserStory> findAllUserStoriesByProjectId(int projectId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select us from UserStory us")
	Collection<UserStory> findAllUserStories();

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

	@Query("select us from UserStory us where us.manager.id =: managerId ")
	Collection<UserStory> findAllUserStoriesByManagerId(int managerId);

	//Delete
	@Query("select c from Contract c where c.project.id = :id")
	Collection<Contract> findManyContractsByProjectId(int id);

	@Query("select pl from ProgressLog pl where pl.contract.id IN :ids")
	Collection<ProgressLog> findManyProgressLogsByContractIds(Set<Integer> ids);

	@Query("select ss from Sponsorship ss where ss.project.id = :id")
	Collection<Sponsorship> findManySponsorshipsByProjectId(int id);

	@Query("select i from Invoice i where i.sponsorship.id IN :ids")
	Collection<Invoice> findManyInvoicesBySponsorshipIds(Set<Integer> ids);

	@Query("select ca from CodeAudit ca where ca.project.id = :id")
	Collection<CodeAudit> findManyCodeAuditsByProjectId(int id);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id IN :id")
	Collection<AuditRecord> findManyAuditsRecordsByCodeAuditsId(Set<Integer> id);

	@Query("select tm from TrainingModule tm where tm.project.id = :id")
	Collection<TrainingModule> findManyTrainingModuleByProjectId(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id IN :id")
	Collection<TrainingSession> findManyTrainingSessionByTrainingModuleId(Set<Integer> id);

}

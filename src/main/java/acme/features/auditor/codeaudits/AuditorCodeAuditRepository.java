
package acme.features.auditor.codeaudits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditrecord.AuditRecord;
import acme.entities.codeaudit.CodeAudit;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select ca from CodeAudit ca where ca.auditor.id = :auditorId")
	Collection<CodeAudit> findAllCodeAuditByAuditorId(int auditorId);

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("select a from Auditor a where a.id = :id")
	Auditor findAuditorById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select ca.project from CodeAudit ca where ca.id = :id ")
	Collection<Project> findProjectByCodeAuditId(int id);

	@Query("select ca from CodeAudit ca where ca.code = :code")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAllAuditRecordByCodeAuditId(int codeAuditId);

	@Query("select ar.mark from AuditRecord ar where ar.codeAudit.id = :codeAuditId group by ar.mark order by count(ar.mark) desc")
	Collection<String> getCountsMark(int codeAuditId);

}

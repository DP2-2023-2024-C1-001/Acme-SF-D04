
package acme.features.auditor.auditordashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select (select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id) from CodeAudit ca where ca.auditor.id = :auditorId AND ca.published = true")
	Collection<Double> countAuditRecordsOfCodeAudit(int auditorId);

	@Query("select count(ca) from CodeAudit ca where ca.auditor.id = :auditorId AND ca.published = true AND ca.type = acme.entities.codeaudit.CodeAuditType.STATIC")
	Integer totalNumberOfCodeAuditsWithTypeStatic(int auditorId);

	@Query("select count(ca) from CodeAudit ca where ca.auditor.id = :auditorId AND ca.published = true AND ca.type = acme.entities.codeaudit.CodeAuditType.DYNAMIC")
	Integer totalNumberOfCodeAuditsWithTypeDynamic(int auditorId);

	@Query("select avg(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id AND ar.published = true) from CodeAudit ca where ca.auditor.id = :auditorId AND ca.published = true")
	Double averageNumberOfAuditRecords(int auditorId);

	@Query("select min(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id AND ar.published = true) from CodeAudit ca where ca.auditor.id = :auditorId AND ca.published = true")
	Integer minimumNumberOfAuditRecords(int auditorId);

	@Query("select max(select count(ar) from AuditRecord ar where ar.codeAudit.id = ca.id AND ar.published = true) from CodeAudit ca where ca.auditor.id = :auditorId AND ca.published = true")
	Integer maximumNumberOfAuditRecords(int auditorId);

	@Query("select avg((UNIX_TIMESTAMP(ar.periodEnd) - UNIX_TIMESTAMP(ar.periodStart))/3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId AND ar.published = true")
	Double averageTimeOfPeriodLegthOfAuditRecord(int auditorId);

	@Query("select stddev((UNIX_TIMESTAMP(ar.periodEnd) - UNIX_TIMESTAMP(ar.periodStart))/3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId AND ar.published = true")
	Double deviationTimeOfPeriodLegthOfAuditRecord(int auditorId);

	@Query("select min((UNIX_TIMESTAMP(ar.periodEnd) - UNIX_TIMESTAMP(ar.periodStart))/3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId AND ar.published = true")
	Double minimumTimeOfPeriodLegthOfAuditRecord(int auditorId);

	@Query("select max((UNIX_TIMESTAMP(ar.periodEnd) - UNIX_TIMESTAMP(ar.periodStart))/3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId AND ar.published = true")
	Double maximumTimeOfPeriodLegthOfAuditRecord(int auditorId);

}

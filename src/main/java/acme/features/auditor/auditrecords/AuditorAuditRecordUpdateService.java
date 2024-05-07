
package acme.features.auditor.auditrecords;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.auditrecord.AuditRecord;
import acme.entities.codeaudit.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordUpdateService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		CodeAudit codeAudit;
		AuditRecord auditRecord;

		auditRecordId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditByAuditRecordId(auditRecordId);
		auditRecord = this.repository.findOneAuditRecordById(auditRecordId);
		status = codeAudit != null && auditRecord != null && !auditRecord.isPublished() && super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "code", "periodStart", "periodEnd", "mark", "link");

	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;

			existing = this.repository.findOneAuditRecordByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "auditor.audit-record.form.error.duplicated");

		}

		if (!super.getBuffer().getErrors().hasErrors("periodEnd") && object.getPeriodStart() != null) {
			Date minimumPeriodEnd;

			minimumPeriodEnd = MomentHelper.deltaFromMoment(object.getPeriodStart(), 1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodEnd(), minimumPeriodEnd), "periodEnd", "auditor.audit-record.form.error.invalidFinalPeriod");
		}
		if (!super.getBuffer().getErrors().hasErrors("mark") && object.getMark() != null) {
			Collection<String> marks = new ArrayList<>();
			marks.add("A+");
			marks.add("A");
			marks.add("B");
			marks.add("C");
			marks.add("F");
			marks.add("F-");
			super.state(marks.contains(object.getMark()), "mark", "auditor.audit-record.form.error.mark");

		}

	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "periodStart", "periodEnd", "mark", "link", "published");
		dataset.put("masterId", object.getCodeAudit().getId());

		super.getResponse().addData(dataset);
	}
}

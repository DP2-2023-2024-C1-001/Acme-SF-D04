
package acme.features.any.auditrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditrecord.AuditRecord;
import acme.entities.codeaudit.CodeAudit;

@Service
public class AnyAuditRecordShowService extends AbstractService<Any, AuditRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditRecordRepository repository;

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
		status = codeAudit != null && codeAudit.isPublished() && auditRecord != null && auditRecord.isPublished();

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
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "periodStart", "periodEnd", "mark", "link", "published");
		dataset.put("masterId", object.getCodeAudit().getId());

		super.getResponse().addData(dataset);
	}
}

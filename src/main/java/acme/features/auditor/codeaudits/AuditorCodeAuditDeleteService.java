
package acme.features.auditor.codeaudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditrecord.AuditRecord;
import acme.entities.codeaudit.CodeAudit;
import acme.entities.codeaudit.CodeAuditType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditDeleteService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		Auditor auditor;

		masterId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(masterId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && !codeAudit.isPublished() && super.getRequest().getPrincipal().hasRole(auditor);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);
		Collection<String> countMarks = this.repository.getCountsMark(object.getId());
		String mode = countMarks.isEmpty() ? null : countMarks.iterator().next();
		object.setMark(mode);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "execution", "type", "correctiveActions", "link");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		Collection<AuditRecord> auditRecords;

		auditRecords = this.repository.findAllAuditRecordByCodeAuditId(object.getId());
		this.repository.deleteAll(auditRecords);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		SelectChoices choicesType;

		choicesType = SelectChoices.from(CodeAuditType.class, object.getType());
		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "execution", "type", "mark", "correctiveActions", "link", "published");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("types", choicesType);

		super.getResponse().addData(dataset);
	}
}

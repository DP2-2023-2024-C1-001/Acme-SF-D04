
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.trainingmodule.Difficult;
import acme.entities.trainingmodule.TrainingModule;
import acme.entities.trainingsession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && !trainingModule.isPublished() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		int totalHours = 0;
		Collection<TrainingSession> trainingSessions;

		trainingSessions = this.repository.findTrainingSessionsByTrainingModuleId(object.getId());

		long diferenciaMili = trainingSessions.stream().mapToLong(x -> x.getFinalPeriod().getTime() - x.getInitialPeriod().getTime()).sum();

		int horasDiferencia = (int) Math.round(diferenciaMili / (1000.0 * 60 * 60));

		totalHours = totalHours + horasDiferencia;
		object.setTotalTime(totalHours);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findProjectById(projectId);

		super.bind(object, "code", "creationMoment", "details", "difficultLevel", "updateMoment", "link");
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("updateMoment") && object.getUpdateMoment() != null)
			super.state(MomentHelper.isAfter(object.getUpdateMoment(), object.getCreationMoment()), "updateMoment", "developer.Training-Modules.form.error.invalidUpdateMoment");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findOneTrainingModuleByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.Training-Modules.form.error.duplicated");
		}

		Collection<TrainingSession> ts;
		ts = this.repository.findTrainingSessionsByTrainingModuleId(object.getId());
		super.state(!ts.isEmpty(), "*", "developer.Training-Modules.form.error.no-training-session");

		super.state(ts.stream().allMatch(x -> x.isPublished() == true), "*", "developer.Training-Modules.form.error.must-be-all-published");

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Collection<Project> projects;
		Dataset dataset;
		SelectChoices choices;
		SelectChoices projectchoices;

		Collection<TrainingSession> ts = this.repository.findTrainingSessionsByTrainingModuleId(object.getId());
		int totalHours = 0;
		for (TrainingSession t : ts) {
			Date inicio = t.getInitialPeriod();
			Date fin = t.getFinalPeriod();
			long diferenciaMilisegundos = fin.getTime() - inicio.getTime();
			int horasDiferencia = (int) Math.round(diferenciaMilisegundos / (1000.0 * 60 * 60));
			totalHours = totalHours + horasDiferencia;
		}

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(Difficult.class, object.getDifficultLevel());
		projectchoices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultLevel", "updateMoment", "link", "totalTime", "published");

		dataset.put("project", projectchoices.getSelected().getKey());
		dataset.put("projects", projectchoices);
		dataset.put("difficultLevel", choices);

		dataset.put("totalTime", totalHours);

		super.getResponse().addData(dataset);
	}
}

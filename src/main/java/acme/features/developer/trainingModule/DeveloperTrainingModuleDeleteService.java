
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.trainingmodule.Difficult;
import acme.entities.trainingmodule.TrainingModule;
import acme.entities.trainingsession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleDeleteService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule tm;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		tm = this.repository.findOneTrainingModuleById(masterId);
		developer = tm == null ? null : tm.getDeveloper();
		status = tm != null && !tm.isPublished() && super.getRequest().getPrincipal().hasRole(developer);

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

		super.bind(object, "code", "creationMoment", "details", "difficultLevel", "updateMoment", "link");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		Collection<TrainingSession> trainingSession;

		trainingSession = this.repository.findTrainingSessionsByTrainingModuleId(object.getId());
		this.repository.deleteAll(trainingSession);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Collection<Project> projects;
		Dataset dataset;
		SelectChoices choices;
		SelectChoices projectchoices;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(Difficult.class, object.getDifficultLevel());
		projectchoices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultLevel", "updateMoment", "link", "totalTime", "published");
		dataset.put("project", projectchoices.getSelected().getKey());
		dataset.put("projects", projectchoices);
		dataset.put("difficultLevel", choices);

		super.getResponse().addData(dataset);
	}
}

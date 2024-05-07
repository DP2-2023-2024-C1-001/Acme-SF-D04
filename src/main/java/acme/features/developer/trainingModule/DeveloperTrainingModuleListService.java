
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingmodule.TrainingModule;
import acme.entities.trainingsession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleListService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TrainingModule> objects;
		int id;
		id = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findAllTrainingModuleByDeveloperId(id);

		for (final TrainingModule tm : objects) {
			int totalHours = 0;
			Collection<TrainingSession> trainingSessions;

			trainingSessions = this.repository.findTrainingSessionsByTrainingModuleId(tm.getId());

			long diferenciaMili = trainingSessions.stream().mapToLong(x -> x.getFinalPeriod().getTime() - x.getInitialPeriod().getTime()).sum();

			int horasDiferencia = (int) Math.round(diferenciaMili / (1000.0 * 60 * 60));

			totalHours = totalHours + horasDiferencia;
			tm.setTotalTime(totalHours);

		}

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "creationMoment", "difficultLevel", "totalTime", "details");

		super.getResponse().addData(dataset);
	}

}

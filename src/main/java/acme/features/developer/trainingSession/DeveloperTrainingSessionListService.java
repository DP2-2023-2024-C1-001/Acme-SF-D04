
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingmodule.TrainingModule;
import acme.entities.trainingsession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule tm;
		masterId = super.getRequest().getData("masterId", int.class);
		tm = this.repository.findOneTrainingModuleById(masterId);
		status = tm != null && super.getRequest().getPrincipal().hasRole(tm.getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TrainingSession> objects;
		int id;

		id = super.getRequest().getData("masterId", int.class);

		objects = this.repository.findAllTrainingSessionByTrainingModuleId(id);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "initialPeriod", "finalPeriod", "location", "instructor");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<TrainingSession> objects) {
		assert objects != null;

		int masterId;
		TrainingModule tm;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		tm = this.repository.findOneTrainingModuleById(masterId);
		showCreate = super.getRequest().getPrincipal().hasRole(tm.getDeveloper());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}

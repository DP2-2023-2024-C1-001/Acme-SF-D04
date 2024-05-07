
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingmodule.TrainingModule;
import acme.entities.trainingsession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

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
		TrainingSession object;
		int masterId;
		TrainingModule tm;

		masterId = super.getRequest().getData("masterId", int.class);
		tm = this.repository.findOneTrainingModuleById(masterId);

		object = new TrainingSession();
		object.setTrainingModule(tm);
		object.setPublished(false);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "initialPeriod", "finalPeriod", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		TrainingModule tm;

		tm = object.getTrainingModule();

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-session.form.error.duplicated");

		}

		if (!super.getBuffer().getErrors().hasErrors("initialPeriod") && object.getFinalPeriod() != null && tm.getCreationMoment() != null) {
			Date minimumFinalPeriod;

			minimumFinalPeriod = MomentHelper.deltaFromMoment(tm.getCreationMoment(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getInitialPeriod(), minimumFinalPeriod), "initialPeriod", "developer.training-session.form.error.too-close-to-training-module-creation-moment");

		}

		if (!super.getBuffer().getErrors().hasErrors("finalPeriod") && object.getInitialPeriod() != null && object.getFinalPeriod() != null) {
			Date minimumInitialPeriod;

			minimumInitialPeriod = MomentHelper.deltaFromMoment(object.getInitialPeriod(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getFinalPeriod(), minimumInitialPeriod), "finalPeriod", "developer.training-session.form.error.too-close-period");

		}
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "initialPeriod", "finalPeriod", "location", "instructor", "contactEmail", "link", "published");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}

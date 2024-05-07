
package acme.features.administrator.objective;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objective.Objective;
import acme.entities.objective.Priority;

@Service
public class AdministratorObjectiveCreateService extends AbstractService<Administrator, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Objective object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Objective();
		object.setInstantiationMoment(moment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Objective object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "description", "priority", "status", "initialPeriod", "finalPeriod");
	}

	@Override
	public void validate(final Objective object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");

		if (!super.getBuffer().getErrors().hasErrors("initialPeriod") && object.getInstantiationMoment() != null)
			super.state(MomentHelper.isAfter(object.getInitialPeriod(), object.getInstantiationMoment()), "initialPeriod", "administrator.objective.form.error.invalidInitialPeriod");

		if (!super.getBuffer().getErrors().hasErrors("finalPeriod") && object.getInitialPeriod() != null)
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInitialPeriod()), "finalPeriod", "administrator.objective.form.error.invalidFinalPeriod");
	}

	@Override
	public void perform(final Objective object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choicesPriority;

		choicesPriority = SelectChoices.from(Priority.class, object.getPriority());

		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "status", "initialPeriod", "finalPeriod");
		dataset.put("priorities", choicesPriority);
		super.getResponse().addData(dataset);
	}
}

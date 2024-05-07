
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progresslog.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);
		status = contract != null && super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);

		object = new ProgressLog();
		object.setContract(contract);
		object.setPublished(false);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProgressLog object) {
		assert object != null;

		super.bind(object, "code", "completeness", "comment", "registrationMoment", "responsiblePerson");
	}

	@Override
	public void validate(final ProgressLog object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			ProgressLog existing;

			existing = this.repository.findOneProgressLogByCode(object.getCode());
			super.state(existing == null, "code", "client.progress-log.form.error.duplicated");

		}

		if (!super.getBuffer().getErrors().hasErrors("completeness")) {
			Double actualCompleteness;
			Double totalCompleteness;
			actualCompleteness = this.repository.findActualCompletenessForAContract(object.getContract().getId());
			if (actualCompleteness == null)
				actualCompleteness = 0.;
			totalCompleteness = actualCompleteness + object.getCompleteness();
			super.state(totalCompleteness <= 100.00, "completeness", "client.progress-log.form.error.completeness");

		}
	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "completeness", "comment", "registrationMoment", "responsiblePerson", "published");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("contractPublished", object.getContract().isPublished());

		super.getResponse().addData(dataset);
	}

}


package acme.features.client.progressLog;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progresslog.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogUpdateService extends AbstractService<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int progressLogId;
		Contract contract;
		ProgressLog progressLog;

		progressLogId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractByProgressLogId(progressLogId);
		progressLog = this.repository.findOneProgressLogById(progressLogId);
		status = contract != null && contract.isPublished() && progressLog != null && !progressLog.isPublished() && super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProgressLogById(id);

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
			super.state(existing == null || existing.getId() == object.getId(), "code", "client.progress-log.form.error.duplicated");

		}

		if (!super.getBuffer().getErrors().hasErrors("registrationMoment")) {
			Date minimumDate;
			ProgressLog lastRegistered;
			lastRegistered = this.repository.findLastPublishedProgressLog(object.getContract().getId());
			if (lastRegistered == null)
				minimumDate = object.getContract().getInstantiationMoment();
			else
				minimumDate = lastRegistered.getRegistrationMoment();
			super.state(MomentHelper.isAfter(object.getRegistrationMoment(), minimumDate), "registrationMoment", "client.progress-log.form.error.invalidDate");

		}

		if (!super.getBuffer().getErrors().hasErrors("completeness")) {
			Double minimumCompleteness;
			ProgressLog lastRegistered;
			lastRegistered = this.repository.findLastPublishedProgressLog(object.getContract().getId());
			if (lastRegistered != null) {
				minimumCompleteness = lastRegistered.getCompleteness();
				super.state(object.getCompleteness() > minimumCompleteness, "completeness", "client.progress-log.form.error.completeness");
			}
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
		dataset.put("masterId", object.getContract().getId());
		dataset.put("contractPublished", object.getContract().isPublished());

		super.getResponse().addData(dataset);
	}
}

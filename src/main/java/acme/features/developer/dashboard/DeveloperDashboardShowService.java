
package acme.features.developer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		DeveloperDashboard dashboard;
		Integer totalNumberOfTrainingModulesWithUpdateMoment;
		Integer totalNumberOfTrainingSessionsWithLink;

		Double averageTimeOfTrainingModules;
		Double deviationTimeOfTrainigModules;
		Integer minimumTimeOfTrainingModules;
		Integer maximumTimeOfTrainingModules;

		totalNumberOfTrainingModulesWithUpdateMoment = this.repository.totalNumberOfTrainingModulesWithUpdateMoment(super.getRequest().getPrincipal().getActiveRoleId());
		totalNumberOfTrainingSessionsWithLink = this.repository.totalNumberOfTrainingSessionsWithLink(super.getRequest().getPrincipal().getActiveRoleId());

		averageTimeOfTrainingModules = this.repository.averageTimeOfTrainingModules(super.getRequest().getPrincipal().getActiveRoleId());
		deviationTimeOfTrainigModules = this.repository.deviationTimeOfTrainigModules(super.getRequest().getPrincipal().getActiveRoleId());
		minimumTimeOfTrainingModules = this.repository.minimumTimeOfTrainingModules(super.getRequest().getPrincipal().getActiveRoleId());
		maximumTimeOfTrainingModules = this.repository.maximumTimeOfTrainingModules(super.getRequest().getPrincipal().getActiveRoleId());

		dashboard = new DeveloperDashboard();

		if (totalNumberOfTrainingModulesWithUpdateMoment == null)
			dashboard.setTotalNumberOfTrainingModulesWithUpdateMoment(0);
		else
			dashboard.setTotalNumberOfTrainingModulesWithUpdateMoment(totalNumberOfTrainingModulesWithUpdateMoment);

		if (totalNumberOfTrainingSessionsWithLink == null)
			dashboard.setTotalNumberOfTrainingModulesWithUpdateMoment(0);
		else
			dashboard.setTotalNumberOfTrainingSessionsWithLink(totalNumberOfTrainingSessionsWithLink);

		if (averageTimeOfTrainingModules != null)
			dashboard.setAverageTimeOfTrainingModules(averageTimeOfTrainingModules);

		if (deviationTimeOfTrainigModules != null)
			dashboard.setDeviationTimeOfTrainigModules(deviationTimeOfTrainigModules);

		if (minimumTimeOfTrainingModules != null)
			dashboard.setMinimumTimeOfTrainingModules(minimumTimeOfTrainingModules);

		if (maximumTimeOfTrainingModules != null)
			dashboard.setMaximumTimeOfTrainingModules(maximumTimeOfTrainingModules);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfTrainingModulesWithUpdateMoment", "totalNumberOfTrainingSessionsWithLink", //
			"averageTimeOfTrainingModules", "deviationTimeOfTrainigModules", //
			"minimumTimeOfTrainingModules", "maximumTimeOfTrainingModules");

		super.getResponse().addData(dataset);
	}

}

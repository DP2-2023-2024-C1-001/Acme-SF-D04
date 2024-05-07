
package acme.features.manager.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.userStory.UserStory;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int managerId = super.getRequest().getPrincipal().getActiveRoleId();
		ManagerDashboard dashboard = new ManagerDashboard();
		Integer countOfUserStoryWithPriorityMust;
		Integer countOfUserStoryWithPriorityShould;
		Integer countOfUserStoryWithPriorityCould;
		Integer countOfUserStoryWithPriorityWont;

		double avgUserStoryEstimatedCost;
		double devUserStoryEstimatedCost;
		double minUserStoryEstimatedCost;
		double maxUserStoryEstimatedCost;

		Collection<Object[]> avgProjectCost;
		Collection<Object[]> devProjectCost;
		Collection<Object[]> minProjectCost;
		Collection<Object[]> maxProjectCost;

		Map<String, Double> mapAvgProjectCost = new HashMap<>();
		Map<String, Double> mapDevProjectCost = new HashMap<>();
		Map<String, Double> mapMinProjectCost = new HashMap<>();
		Map<String, Double> mapMaxProjectCost = new HashMap<>();

		Collection<Project> projects = this.repository.findAllProjectsByManagerId(managerId);
		Collection<UserStory> userStories = this.repository.findAllUserStoriesByManagerId(managerId);

		dashboard.setMustUserStories(0);
		dashboard.setShouldUserStories(0);
		dashboard.setCouldUserStories(0);
		dashboard.setWontUserStories(0);

		dashboard.setAverageCostOfUserStory(Double.NaN);
		dashboard.setDeviationCostOfUserStory(Double.NaN);
		dashboard.setMinimumCostOfUserStory(Double.NaN);
		dashboard.setMaximumCostOfUserStory(Double.NaN);

		if (!userStories.isEmpty()) {
			countOfUserStoryWithPriorityMust = this.repository.countOfUserStoryWithPriorityMust(managerId);
			countOfUserStoryWithPriorityShould = this.repository.countOfUserStoryWithPriorityShould(managerId);
			countOfUserStoryWithPriorityCould = this.repository.countOfUserStoryWithPriorityCould(managerId);
			countOfUserStoryWithPriorityWont = this.repository.countOfUserStoryWithPriorityWont(managerId);

			avgUserStoryEstimatedCost = this.repository.avgUserStoryEstimatedCost(managerId);
			devUserStoryEstimatedCost = this.repository.devUserStoryEstimatedCost(managerId);
			minUserStoryEstimatedCost = this.repository.minUserStoryEstimatedCost(managerId);
			maxUserStoryEstimatedCost = this.repository.maxUserStoryEstimatedCost(managerId);

			dashboard.setMustUserStories(countOfUserStoryWithPriorityMust);
			dashboard.setShouldUserStories(countOfUserStoryWithPriorityShould);
			dashboard.setCouldUserStories(countOfUserStoryWithPriorityCould);
			dashboard.setWontUserStories(countOfUserStoryWithPriorityWont);

			dashboard.setAverageCostOfUserStory(avgUserStoryEstimatedCost);
			dashboard.setDeviationCostOfUserStory(devUserStoryEstimatedCost);
			dashboard.setMinimumCostOfUserStory(minUserStoryEstimatedCost);
			dashboard.setMaximumCostOfUserStory(maxUserStoryEstimatedCost);
		}

		if (!projects.isEmpty()) {
			avgProjectCost = this.repository.avgProjectCost(managerId);
			devProjectCost = this.repository.devProjectCost(managerId);
			minProjectCost = this.repository.minProjectCost(managerId);
			maxProjectCost = this.repository.maxProjectCost(managerId);

			mapAvgProjectCost = this.repository.convertToMap(avgProjectCost);
			mapDevProjectCost = this.repository.convertToMap(devProjectCost);
			mapMinProjectCost = this.repository.convertToMap(minProjectCost);
			mapMaxProjectCost = this.repository.convertToMap(maxProjectCost);

			dashboard.setDeviationCostOfProject(mapDevProjectCost);
			dashboard.setAverageCostOfProject(mapAvgProjectCost);
			dashboard.setMinimumCostOfProject(mapMinProjectCost);
			dashboard.setMaximumCostOfProject(mapMaxProjectCost);

		}

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"mustUserStories", "shouldUserStories", //
			"couldUserStories", "wontUserStories", //
			"averageCostOfUserStory", "deviationCostOfUserStory",//
			"minimumCostOfUserStory", "maximumCostOfUserStory",//
			"averageCostOfProject", "deviationCostOfProject", //
			"minimumCostOfProject", "maximumCostOfProject");

		super.getResponse().addData(dataset);
	}
}

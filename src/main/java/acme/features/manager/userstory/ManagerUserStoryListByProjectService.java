
package acme.features.manager.userstory;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListByProjectService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int managerId;
		int projectId;
		Project project;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findOneProjectById(projectId);

		status = super.getRequest().getPrincipal().getActiveRole() == Manager.class //
			&& project.getManager().equals(this.repository.findOneManagerById(managerId));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		objects = this.repository.findUserStoriesByProjectId(projectId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "title", "estimatedCost", "priority");
		payload = String.format(//
			"%s; %s; %s; %s", //
			object.getDescription(), //
			object.getManager().getIdentity().getFullName(), //
			object.getAcceptanceCriteria(), //
			object.getLink());
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}

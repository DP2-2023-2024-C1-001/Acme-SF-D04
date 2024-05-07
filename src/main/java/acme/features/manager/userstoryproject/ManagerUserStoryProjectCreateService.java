
package acme.features.manager.userstoryproject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project.UserStoryProject;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryProjectCreateService extends AbstractService<Manager, UserStoryProject> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerUserStoryProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStoryProject object;

		object = new UserStoryProject();
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStoryProject object) {
		assert object != null;

		super.bind(object, "userStory", "project");
	}

	@Override
	public void validate(final UserStoryProject object) {
		assert object != null;
		Project project;
		UserStory userStory;

		project = object.getProject();
		userStory = object.getUserStory();

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			UserStoryProject existing;

			existing = this.repository.findOneAssignationByProjectIdAndUserStoryId(project.getId(), userStory.getId());
			super.state(existing == null, "project", "manager.user-story-project.form.error.existing-user-story-project");

			super.state(project.isDraftMode(), "project", "manager.user-story-project.form.error.published-project");
		}

	}

	@Override
	public void perform(final UserStoryProject object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStoryProject object) {
		assert object != null;

		int managerId = super.getRequest().getPrincipal().getActiveRoleId();

		//		Collection<Project> projects = this.repository.findProjectsByManagerId(managerId);
		Collection<Project> projects = this.repository.findDraftModeProjectsByManagerId(managerId);
		SelectChoices projectChoices = SelectChoices.from(projects, "title", object.getProject());

		Collection<UserStory> userStories = this.repository.findUserStoriesByManagerId(managerId);
		Collection<UserStory> publishedUserStories = this.repository.findAllPublishedUserStories();
		Set<UserStory> userStoriesBuenas = new HashSet<>(userStories);
		userStoriesBuenas.addAll(publishedUserStories);
		SelectChoices userStoryChoices = SelectChoices.from(userStoriesBuenas, "title", object.getUserStory());

		Dataset dataset = super.unbind(object, "project", "userStory");

		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);
		dataset.put("userStory", userStoryChoices.getSelected().getKey());
		dataset.put("userStories", userStoryChoices);

		super.getResponse().addData(dataset);
	}

}

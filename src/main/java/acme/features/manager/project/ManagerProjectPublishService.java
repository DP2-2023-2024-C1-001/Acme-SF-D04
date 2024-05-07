
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		boolean status;
		int projectId;
		Project project;
		Manager manager;

		projectId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(projectId);
		manager = project == null ? null : project.getManager();

		status = project != null && project.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "projectAbstract", "link", "cost", "indicator");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("indicator"))
			super.state(object.isIndicator() == false, "indicator", "manager.project.form.error.hasFatalError");

		Collection<UserStory> userStories;
		int totalUserStories;

		userStories = this.repository.findAllUserStoriesByProjectId(object.getId());
		boolean allUserStoriesPublished = userStories.stream().allMatch(us -> !us.isDraftMode());
		totalUserStories = userStories.size();

		super.state(totalUserStories >= 1, "*", "manager.project.form.error.not-enough-user-stories");
		super.state(allUserStoriesPublished, "*", "manager.project.form.error.not-all-user-stories-published");

	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "projectAbstract", "link", "cost", "indicator", "draftMode");

		super.getResponse().addData(dataset);

	}
}

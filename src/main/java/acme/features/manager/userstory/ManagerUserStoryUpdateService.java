
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.userStory.Priority;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryUpdateService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int managerId;
		int userStoryId;
		UserStory userStory;

		managerId = super.getRequest().getPrincipal().getAccountId();
		userStoryId = super.getRequest().getData("id", int.class);
		userStory = this.repository.findOneUserStoryById(userStoryId);

		status = userStory != null && userStory.isDraftMode() && userStory.getManager().getUserAccount().getId() == managerId;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		UserStory userStory = this.repository.findOneUserStoryById(id);

		super.getBuffer().addData(userStory);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "priority");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("estimatedCost")) {
			Integer time;
			time = object.getEstimatedCost();
			super.state(time > 0, "estimatedCost", "manager.user-story.form.error.negativeTime");
		}
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		boolean isMine;
		UserStory userStory;
		Manager manager;

		userStory = this.repository.findOneUserStoryById(super.getRequest().getData("id", int.class));
		manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());

		isMine = userStory.getManager().equals(manager);

		SelectChoices choices = SelectChoices.from(Priority.class, object.getPriority());

		Dataset dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "priority", "draftMode");

		dataset.put("priorityChoices", choices);
		dataset.put("isMine", isMine);

		super.getResponse().addData(dataset);
	}

}


package acme.features.manager.userstoryproject;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.UserStoryProject;
import acme.roles.Manager;

@Controller
public class ManagerUserStoryProjectController extends AbstractController<Manager, UserStoryProject> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected ManagerUserStoryProjectListService	listService;

	@Autowired
	private ManagerUserStoryProjectShowService		showService;

	@Autowired
	private ManagerUserStoryProjectCreateService	createService;

	@Autowired
	protected ManagerUserStoryProjectDeleteService	deleteService;


	// Constructors -----------------------------------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);

	}
}

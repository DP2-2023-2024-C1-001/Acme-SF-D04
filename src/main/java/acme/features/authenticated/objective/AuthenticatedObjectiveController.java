
package acme.features.authenticated.objective;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.objective.Objective;

@Controller
public class AuthenticatedObjectiveController extends AbstractController<Authenticated, Objective> {

	@Autowired
	private AuthenticatedObjectiveListService	listService;

	@Autowired
	private AuthenticatedObjectiveShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}


package acme.features.authenticated.risk;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.risk.Risk;

@Controller
public class AuthenticatedRiskController extends AbstractController<Authenticated, Risk> {

	@Autowired
	private AuthenticatedRiskListService	listService;

	@Autowired
	private AuthenticatedRiskShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}

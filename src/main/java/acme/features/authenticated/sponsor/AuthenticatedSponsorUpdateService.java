
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.roles.Sponsor;

@Service
public class AuthenticatedSponsorUpdateService extends AbstractService<Authenticated, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedSponsorRepository repository;

	// AbstractService interface -----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsor object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneSponsorByUserAccountId(userAccountId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsor object) {
		assert object != null;

		super.bind(object, "name", "benefits", "webPage", "email");
	}

	@Override
	public void validate(final Sponsor object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("webPage") && object.getWebPage() != null)
			super.state(object.getWebPage().length() >= 7 && object.getWebPage().length() <= 255 || object.getWebPage().length() == 0, "webPage", "authenticated.sponsor.form.error.webPage");

		if (!super.getBuffer().getErrors().hasErrors("email") && object.getEmail() != null)
			super.state(object.getEmail().length() >= 6 && object.getEmail().length() <= 254 || object.getEmail().length() == 0, "email", "authenticated.sponsor.form.error.email");
	}

	@Override
	public void perform(final Sponsor object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsor object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "name", "benefits", "webPage", "email");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}

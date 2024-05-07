
package acme.features.authenticated.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.UserAccount;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.notice.Notice;

@Service
public class AuthenticatedNoticeCreateService extends AbstractService<Authenticated, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedNoticeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Notice object;
		Date moment;
		String username;
		UserAccount authorAccount;
		String author;

		moment = MomentHelper.getCurrentMoment();

		object = new Notice();
		object.setInstantiationMoment(moment);

		username = super.getRequest().getPrincipal().getUsername();
		authorAccount = this.repository.findOneUserAccountByUsername(username);
		author = username + "-" + authorAccount.getIdentity().getFullName();
		object.setAuthor(author);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Notice object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "message", "emailAddress", "link", "author");
	}

	@Override
	public void validate(final Notice object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "authenticated.notice.form.error.confirmation");

		if (!super.getBuffer().getErrors().hasErrors("author")) {

			String username;
			UserAccount authorAccount;
			String author;

			username = super.getRequest().getPrincipal().getUsername();
			authorAccount = this.repository.findOneUserAccountByUsername(username);
			author = username + "-" + authorAccount.getIdentity().getFullName();

			super.state(object.getAuthor().equals(author), "author", "authenticated.notice.form.error.author");

		}
	}

	@Override
	public void perform(final Notice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "title", "message", "emailAddress", "link", "author");

		super.getResponse().addData(dataset);
	}
}

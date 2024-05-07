
package acme.features.administrator.systemconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.systemconfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorSystemConfigurationRepository repository;

	// AbstractService interface -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		status = principal.hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		SystemConfiguration object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSystemConfigurationById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final SystemConfiguration object) {
		assert object != null;

		super.bind(object, "acceptedCurrencies", "systemCurrency");
	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("systemCurrency")) {
			final String acceptedCurrencies = object.getAcceptedCurrencies();
			final String systemCurrency = object.getSystemCurrency();

			String[] letras = object.getAcceptedCurrencies().split(",");

			if (letras[0].trim().equals(object.getSystemCurrency()))
				super.state(acceptedCurrencies.contains(systemCurrency + " "), "systemCurrency", "administrator.config.form.error.systemCurrency");
			else
				super.state(acceptedCurrencies.contains(" " + systemCurrency + " "), "systemCurrency", "administrator.config.form.error.systemCurrency");

		}

	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		String palabras = object.getAcceptedCurrencies();

		String resultado = " ";

		for (String p : palabras.split(","))
			resultado = resultado + " " + p.trim() + " ,";

		object.setAcceptedCurrencies(resultado);
		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "acceptedCurrencies", "systemCurrency");

		super.getResponse().addData(dataset);
	}

}

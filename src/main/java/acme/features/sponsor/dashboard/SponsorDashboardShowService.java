
package acme.features.sponsor.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SponsorDashboard dashboard;
		Integer totalNumberOfInvoicesWithTaxLessOrEquals21Percent;
		Integer totalNumberOfSponsorshipWithLink;

		Collection<Object[]> averageAmountOfSponsorship;
		Collection<Object[]> deviationAmountOfSponsorship;
		Collection<Object[]> minimumAmountOfSponsorship;
		Collection<Object[]> maximumAmountOfSponsorship;

		Collection<Object[]> averageQuantityOfInvoice;
		Collection<Object[]> deviationQuantityOfInvoice;
		Collection<Object[]> minimumQuantityOfInvoice;
		Collection<Object[]> maximumQuantityOfInvoice;

		Map<String, Double> mapAverageAmountOfSponsorship;
		Map<String, Double> mapDeviationAmountOfSponsorship;
		Map<String, Double> mapMinimumAmountOfSponsorship;
		Map<String, Double> mapMaximumAmountOfSponsorship;

		Map<String, Double> mapAverageQuantityOfInvoice;
		Map<String, Double> mapDeviationQuantityOfInvoice;
		Map<String, Double> mapMinimumQuantityOfInvoice;
		Map<String, Double> mapMaximumQuantityOfInvoice;
		//--------
		totalNumberOfInvoicesWithTaxLessOrEquals21Percent = this.repository.totalNumberOfInvoicesWithTaxLessOrEquals21Percent(super.getRequest().getPrincipal().getActiveRoleId());
		totalNumberOfSponsorshipWithLink = this.repository.totalNumberOfSponsorshipWithLink(super.getRequest().getPrincipal().getActiveRoleId());

		//sponsorship---------
		averageAmountOfSponsorship = this.repository.averageAmountOfSponsorship(super.getRequest().getPrincipal().getActiveRoleId());
		deviationAmountOfSponsorship = this.repository.deviationAmountOfSponsorship(super.getRequest().getPrincipal().getActiveRoleId());
		minimumAmountOfSponsorship = this.repository.minimumAmountOfSponsorship(super.getRequest().getPrincipal().getActiveRoleId());
		maximumAmountOfSponsorship = this.repository.maximumAmountOfSponsorship(super.getRequest().getPrincipal().getActiveRoleId());

		//invoice-------------
		averageQuantityOfInvoice = this.repository.averageQuantityOfInvoice(super.getRequest().getPrincipal().getActiveRoleId());
		deviationQuantityOfInvoice = this.repository.deviationQuantityOfInvoice(super.getRequest().getPrincipal().getActiveRoleId());
		minimumQuantityOfInvoice = this.repository.minimumQuantityOfInvoice(super.getRequest().getPrincipal().getActiveRoleId());
		maximumQuantityOfInvoice = this.repository.maximumQuantityOfInvoice(super.getRequest().getPrincipal().getActiveRoleId());

		mapAverageAmountOfSponsorship = new HashMap<>();
		mapDeviationAmountOfSponsorship = new HashMap<>();
		mapMinimumAmountOfSponsorship = new HashMap<>();
		mapMaximumAmountOfSponsorship = new HashMap<>();

		mapAverageQuantityOfInvoice = new HashMap<>();
		mapDeviationQuantityOfInvoice = new HashMap<>();
		mapMinimumQuantityOfInvoice = new HashMap<>();
		mapMaximumQuantityOfInvoice = new HashMap<>();

		mapAverageAmountOfSponsorship = this.repository.convertToMap(averageAmountOfSponsorship);
		mapDeviationAmountOfSponsorship = this.repository.convertToMap(deviationAmountOfSponsorship);
		mapMinimumAmountOfSponsorship = this.repository.convertToMap(minimumAmountOfSponsorship);
		mapMaximumAmountOfSponsorship = this.repository.convertToMap(maximumAmountOfSponsorship);

		mapAverageQuantityOfInvoice = this.repository.convertToMap(averageQuantityOfInvoice);
		mapDeviationQuantityOfInvoice = this.repository.convertToMap(deviationQuantityOfInvoice);
		mapMinimumQuantityOfInvoice = this.repository.convertToMap(minimumQuantityOfInvoice);
		mapMaximumQuantityOfInvoice = this.repository.convertToMap(maximumQuantityOfInvoice);

		dashboard = new SponsorDashboard();
		dashboard.setTotalNumberOfInvoicesWithTaxLessOrEquals21Percent(totalNumberOfInvoicesWithTaxLessOrEquals21Percent);
		dashboard.setTotalNumberOfSponsorshipWithLink(totalNumberOfSponsorshipWithLink);
		dashboard.setAverageAmountOfSponsorship(mapAverageAmountOfSponsorship);
		dashboard.setDeviationAmountOfSponsorship(mapDeviationAmountOfSponsorship);
		dashboard.setMinimumAmountOfSponsorship(mapMinimumAmountOfSponsorship);
		dashboard.setMaximumAmountOfSponsorship(mapMaximumAmountOfSponsorship);
		dashboard.setAverageQuantityOfInvoice(mapAverageQuantityOfInvoice);
		dashboard.setDeviationQuantityOfInvoice(mapDeviationQuantityOfInvoice);
		dashboard.setMinimumQuantityOfInvoice(mapMinimumQuantityOfInvoice);
		dashboard.setMaximumQuantityOfInvoice(mapMaximumQuantityOfInvoice);

		super.getBuffer().addData(dashboard);

	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalNumberOfInvoicesWithTaxLessOrEquals21Percent", "totalNumberOfSponsorshipWithLink", //
			"averageAmountOfSponsorship", "deviationAmountOfSponsorship", "minimumAmountOfSponsorship", "maximumAmountOfSponsorship", //
			"averageQuantityOfInvoice", "deviationQuantityOfInvoice", "minimumQuantityOfInvoice", "maximumQuantityOfInvoice");

		super.getResponse().addData(dataset);

	}

}

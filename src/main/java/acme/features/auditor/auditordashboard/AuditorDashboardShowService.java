
package acme.features.auditor.auditordashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditorDashboard dashboard;

		Integer totalNumberOfCodeAuditsWithTypeStatic;
		Integer totalNumberOfCodeAuditsWithTypeDynamic;

		Double averageNumberOfAuditRecords;
		Double deviationNumberOfAuditRecords;
		Integer minimumNumberOfAuditRecords;
		Integer maximumNumberOfAuditRecords;

		Double averageTimeOfPeriodLegthOfAuditRecord;
		Double deviationTimeOfPeriodLegthOfAuditRecord;
		Double minimumTimeOfPeriodLegthOfAuditRecord;
		Double maximumTimeOfPeriodLegthOfAuditRecord;

		totalNumberOfCodeAuditsWithTypeStatic = this.repository.totalNumberOfCodeAuditsWithTypeStatic(super.getRequest().getPrincipal().getActiveRoleId());
		totalNumberOfCodeAuditsWithTypeDynamic = this.repository.totalNumberOfCodeAuditsWithTypeDynamic(super.getRequest().getPrincipal().getActiveRoleId());

		averageNumberOfAuditRecords = this.repository.averageNumberOfAuditRecords(super.getRequest().getPrincipal().getActiveRoleId());
		deviationNumberOfAuditRecords = this.deviation(this.repository.countAuditRecordsOfCodeAudit(super.getRequest().getPrincipal().getActiveRoleId()));
		minimumNumberOfAuditRecords = this.repository.minimumNumberOfAuditRecords(super.getRequest().getPrincipal().getActiveRoleId());
		maximumNumberOfAuditRecords = this.repository.maximumNumberOfAuditRecords(super.getRequest().getPrincipal().getActiveRoleId());
		averageTimeOfPeriodLegthOfAuditRecord = this.repository.averageTimeOfPeriodLegthOfAuditRecord(super.getRequest().getPrincipal().getActiveRoleId());
		deviationTimeOfPeriodLegthOfAuditRecord = this.repository.deviationTimeOfPeriodLegthOfAuditRecord(super.getRequest().getPrincipal().getActiveRoleId());
		minimumTimeOfPeriodLegthOfAuditRecord = this.repository.minimumTimeOfPeriodLegthOfAuditRecord(super.getRequest().getPrincipal().getActiveRoleId());
		maximumTimeOfPeriodLegthOfAuditRecord = this.repository.maximumTimeOfPeriodLegthOfAuditRecord(super.getRequest().getPrincipal().getActiveRoleId());

		dashboard = new AuditorDashboard();
		dashboard.setTotalNumberOfCodeAuditsWithTypeStatic(totalNumberOfCodeAuditsWithTypeStatic);
		dashboard.setTotalNumberOfCodeAuditsWithTypeDynamic(totalNumberOfCodeAuditsWithTypeDynamic);

		if (averageNumberOfAuditRecords != null)
			dashboard.setAverageNumberOfAuditRecords(averageNumberOfAuditRecords);

		if (deviationNumberOfAuditRecords != null)
			dashboard.setDeviationNumberOfAuditRecords(deviationNumberOfAuditRecords);

		if (minimumNumberOfAuditRecords != null)
			dashboard.setMinimumNumberOfAuditRecords(minimumNumberOfAuditRecords);

		if (maximumNumberOfAuditRecords != null)
			dashboard.setMaximumNumberOfAuditRecords(maximumNumberOfAuditRecords);

		if (averageTimeOfPeriodLegthOfAuditRecord != null)
			dashboard.setAverageTimeOfPeriodLegthOfAuditRecord(averageTimeOfPeriodLegthOfAuditRecord);

		if (deviationTimeOfPeriodLegthOfAuditRecord != null)
			dashboard.setDeviationTimeOfPeriodLegthOfAuditRecord(deviationTimeOfPeriodLegthOfAuditRecord);

		if (minimumTimeOfPeriodLegthOfAuditRecord != null)
			dashboard.setMinimumTimeOfPeriodLegthOfAuditRecord(minimumTimeOfPeriodLegthOfAuditRecord);

		if (maximumTimeOfPeriodLegthOfAuditRecord != null)
			dashboard.setMaximumTimeOfPeriodLegthOfAuditRecord(maximumTimeOfPeriodLegthOfAuditRecord);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfCodeAuditsWithTypeStatic", "totalNumberOfCodeAuditsWithTypeDynamic", //
			"averageNumberOfAuditRecords", "deviationNumberOfAuditRecords", //
			"minimumNumberOfAuditRecords", "maximumNumberOfAuditRecords", "averageTimeOfPeriodLegthOfAuditRecord", "deviationTimeOfPeriodLegthOfAuditRecord", //
			"minimumTimeOfPeriodLegthOfAuditRecord", "maximumTimeOfPeriodLegthOfAuditRecord");

		super.getResponse().addData(dataset);
	}

	public Double deviation(final Collection<Double> set) {
		Double res;
		Double aux;
		res = null;
		if (!set.isEmpty()) {
			Double average = this.average(set);
			aux = 0.0;
			for (final Double s : set)
				aux += Math.pow(s - average, 2);
			res = Math.sqrt(aux / set.size());
		}
		return res;
	}

	private Double average(final Collection<Double> set) {
		double sum = 0.0;
		for (Double s : set)
			sum += s;
		return sum / set.size();
	}
}

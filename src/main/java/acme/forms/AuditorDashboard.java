
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							totalNumberOfCodeAuditsWithTypeStatic;
	int							totalNumberOfCodeAuditsWithTypeDynamic;

	double						averageNumberOfAuditRecords;
	double						deviationNumberOfAuditRecords;
	double						minimumNumberOfAuditRecords;
	double						maximumNumberOfAuditRecords;

	double						averageTimeOfPeriodLegthOfAuditRecord;
	double						deviationTimeOfPeriodLegthOfAuditRecord;
	double						minimumTimeOfPeriodLegthOfAuditRecord;
	double						maximumTimeOfPeriodLegthOfAuditRecord;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}

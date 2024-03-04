
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

	Integer						totalNumberOfCodeAuditsWithTypeStatic;
	Integer						totalNumberOfCodeAuditsWithTypeDynamic;

	Double						averageNumberOfAuditRecords;
	Double						deviationNumberOfAuditRecords;
	Double						minimumNumberOfAuditRecords;
	Double						maximumNumberOfAuditRecords;

	Double						averageTimeOfPeriodLegthOfAuditRecord;
	Double						deviationTimeOfPeriodLegthOfAuditRecord;
	Double						minimumTimeOfPeriodLegthOfAuditRecord;
	Double						maximumTimeOfPeriodLegthOfAuditRecord;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}

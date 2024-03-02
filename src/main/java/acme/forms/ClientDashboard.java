
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfProgressLogsWithCompletenessRateBelow25Percent;
	Integer						totalNumberOfProgressLogsWithCompletenessRateBetween25And50Percent;
	Integer						totalNumberOfProgressLogsWithCompletenessRateBetween50And75Percent;
	Integer						totalNumberOfProgressLogsWithCompletenessRateAbove75Percent;

	Double						averageBudgetOfContracts;
	Double						deviationBudgetOfContracts;
	Double						minimumBudgetOfContracts;
	Double						maximumBudgetOfContracts;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}

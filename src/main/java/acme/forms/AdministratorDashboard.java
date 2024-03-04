
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfPrincipalsWithEachRole;
	Double						ratioOfNoticesWithEmailAndLink;
	Double						ratioOfCriticalObjectives;
	Double						ratioOfNonCriticalObjectives;

	Double						averageOfTheValueInTheRisks;
	Double						deviationOfTheValueInTheRisks;
	Double						minimumOfTheValueInTheRisks;
	Double						maximumOfTheValueInTheRisks;

	Double						averageOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	Double						deviationOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	Double						minimumOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	Double						maximumOfTheNumberOfClaimsPostedOverTheLast10Weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}

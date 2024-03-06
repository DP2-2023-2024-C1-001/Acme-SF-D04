
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

	int							totalNumberOfPrincipalsWithEachRole;
	double						ratioOfNoticesWithEmailAndLink;
	double						ratioOfCriticalObjectives;
	double						ratioOfNonCriticalObjectives;

	double						averageOfTheValueInTheRisks;
	double						deviationOfTheValueInTheRisks;
	double						minimumOfTheValueInTheRisks;
	double						maximumOfTheValueInTheRisks;

	double						averageOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	double						deviationOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	double						minimumOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	double						maximumOfTheNumberOfClaimsPostedOverTheLast10Weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}

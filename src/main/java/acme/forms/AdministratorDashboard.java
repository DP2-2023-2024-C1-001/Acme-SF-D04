
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

	int							totalNumberOfPrincipalsWithRoleAdministrator;
	int							totalNumberOfPrincipalsWithRoleAuditor;
	int							totalNumberOfPrincipalsWithRoleClient;
	int							totalNumberOfPrincipalsWithRoleConsumer;
	int							totalNumberOfPrincipalsWithRoleDeveloper;
	int							totalNumberOfPrincipalsWithRoleManager;
	int							totalNumberOfPrincipalsWithRoleProvider;
	int							totalNumberOfPrincipalsWithRoleSponsor;

	double						ratioOfNoticesWithEmailAndLink;
	double						ratioOfCriticalObjectives;
	double						ratioOfNonCriticalObjectives;

	double						averageOfTheValueInTheRisks;
	double						deviationOfTheValueInTheRisks;
	double						minimumOfTheValueInTheRisks;
	double						maximumOfTheValueInTheRisks;

	double						averageOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	double						deviationOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	int							minimumOfTheNumberOfClaimsPostedOverTheLast10Weeks;
	int							maximumOfTheNumberOfClaimsPostedOverTheLast10Weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}

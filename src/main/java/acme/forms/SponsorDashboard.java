
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfInvoicesWithTaxLessOrEquals21Percent;
	Integer						totalNumberOfSponsorshipWithLink;

	Double						averageAmountOfSponsorship;
	Double						deviationAmountOfSponsorship;
	Double						minimumAmountOfSponsorship;
	Double						maximumAmountOfSponsorship;

	Double						averageQuantityOfInvoice;
	Double						deviationQuantityOfInvoice;
	Double						minimumQuantityOfInvoice;
	Double						maximumQuantityOfInvoice;

}

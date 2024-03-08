
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

	int							totalNumberOfInvoicesWithTaxLessOrEquals21Percent;
	int							totalNumberOfSponsorshipWithLink;

	double						averageAmountOfSponsorship;
	double						deviationAmountOfSponsorship;
	double						minimumAmountOfSponsorship;
	double						maximumAmountOfSponsorship;

	double						averageQuantityOfInvoice;
	double						deviationQuantityOfInvoice;
	double						minimumQuantityOfInvoice;
	double						maximumQuantityOfInvoice;

}

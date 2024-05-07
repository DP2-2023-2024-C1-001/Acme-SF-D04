
package acme.forms;

import java.util.Map;

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

	Map<String, Double>			averageAmountOfSponsorship;
	Map<String, Double>			deviationAmountOfSponsorship;
	Map<String, Double>			minimumAmountOfSponsorship;
	Map<String, Double>			maximumAmountOfSponsorship;

	Map<String, Double>			averageQuantityOfInvoice;
	Map<String, Double>			deviationQuantityOfInvoice;
	Map<String, Double>			minimumQuantityOfInvoice;
	Map<String, Double>			maximumQuantityOfInvoice;

}

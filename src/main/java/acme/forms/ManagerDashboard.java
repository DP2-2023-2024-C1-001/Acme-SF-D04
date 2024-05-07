
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							mustUserStories;
	int							shouldUserStories;
	int							couldUserStories;
	int							wontUserStories;

	double						averageCostOfUserStory;
	double						deviationCostOfUserStory;
	double						minimumCostOfUserStory;
	double						maximumCostOfUserStory;

	Map<String, Double>			averageCostOfProject;
	Map<String, Double>			deviationCostOfProject;
	Map<String, Double>			minimumCostOfProject;
	Map<String, Double>			maximumCostOfProject;
}

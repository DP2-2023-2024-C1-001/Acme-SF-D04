
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.entities.userStory.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashBoard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<Priority, Integer>		totalUserStoriesNumberByPriority;

	double						averageAmountOfUserStory;
	double						deviationAmountOfUserStory;
	double						minimumAmountOfUserStory;
	double						maximumAmountOfUserStory;

	double						averageAmountOfProject;
	double						deviationAmountOfProject;
	double						minimumAmountOfProject;
	double						maximumAmountOfProject;
}

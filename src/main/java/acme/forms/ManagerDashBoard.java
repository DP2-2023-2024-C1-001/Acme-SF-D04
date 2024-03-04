
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;

public class ManagerDashBoard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<Priority, Integer>		totalUserStoriesNumberByPriority;

	Double						averageAmountOfUserStory;
	Double						deviationAmountOfUserStory;
	Double						minimumAmountOfUserStory;
	Double						maximumAmountOfUserStory;

	Double						averageAmountOfProject;
	Double						deviationAmountOfProject;
	Double						minimumAmountOfProject;
	Double						maximumAmountOfProject;
}

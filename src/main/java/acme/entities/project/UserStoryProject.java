
package acme.entities.project;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.client.data.AbstractEntity;
import acme.entities.userStory.UserStory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "project_id"), @Index(columnList = "user_story_id"), @Index(columnList = "project_id,user_story_id")
})
public class UserStoryProject extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne
	private UserStory			userStory;

}

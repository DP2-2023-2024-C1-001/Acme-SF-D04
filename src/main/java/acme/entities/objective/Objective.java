
package acme.entities.objective;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Objective extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	@NotNull
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 76)
	@NotNull
	private String				title;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@NotNull
	private Priority			priority;

	@NotNull
	private Boolean				status;

	@NotNull
	@FutureOrPresent
	private Duration			duration;

	@URL
	private String				link;
}

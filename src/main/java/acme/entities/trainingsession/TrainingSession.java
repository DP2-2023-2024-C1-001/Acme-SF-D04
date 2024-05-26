
package acme.entities.trainingsession;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.trainingmodule.TrainingModule;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code"), @Index(columnList = "trainingModule_id")
})
public class TrainingSession extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "TS-[A-Z]{1,3}-[0-9]{3}")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				initialPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				finalPeriod;

	@NotBlank
	@Length(max = 75)
	private String				location;

	@NotBlank
	@Length(max = 75)
	private String				instructor;

	@Email
	@NotBlank
	@Length(min = 6, max = 254)
	private String				contactEmail;

	@URL
	private String				link;

	private boolean				published;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private TrainingModule		trainingModule;

}

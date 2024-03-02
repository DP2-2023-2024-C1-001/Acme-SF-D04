
package acme.entities.invoice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.sponsorship.Sponsorship;

public class Invoice extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationTime;

	// at least one month ahead the registration time
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				dueDate;

	@Positive
	@NotNull
	private double				quantity;

	@Min(0)
	@Max(100)
	@NotNull
	private double				tax;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------


	@Transient
	public double totalAmount() {
		double result;
		double amount;

		amount = this.quantity * this.tax / 100;

		result = this.quantity + amount;

		return result;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsorship sponsorship;

}

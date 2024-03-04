
package acme.roles;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class Manager {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			degree;

	@NotBlank
	@Length(max = 100)
	protected String			overview;

	@NotBlank
	@Length(max = 100)
	protected String			certifications;

	@URL
	protected String			link;
}

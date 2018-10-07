package am.chronograph.domain.email;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import am.chronograph.domain.AbstractEntity;


@Entity
@Table(name = "email_template")
public class EmailTemplate extends AbstractEntity {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = -459204577826357348L;

	/**
	 * The type of the email message.
	 * 
	 * @author tigranbabloyan
	 *
	 */
	public enum Type {
		ACTIVATION("Lastname", "Firstname"),
		ASSIGNMENT("Lastname", "Firstname", "Step Name",
				"Process Name"),
		NOTIFICATION("Lastname", "Firstname", "Step Name", "Process Status"),
		PROCESS_FAIL("Lastname", "Firstname", "Process Name", "Step Assigne","Step Name"),
		PROCESS_STEP_FAIL("Lastname", "Firstname", "Process Name", "Step Assigne",
								"Step Name"),
		PROCESS_SUCCESS("Lastname", "Firstname","Process Name"),
		TASK_OVERDUE("Lastname", "Firstname", "Process Name"),
		PROCESS_STEP_TERMINATE("Lastname", "Firstname","Process Name","Step Name"),
		PROCESS_STEP_POSTPONE("Lastname", "Firstname","Process Name","Step Name"),
		PROCESS_STEP_SKIP("Lastname", "Firstname","Process Name","Step Name"),
		ACCEPT_CS("Lastname", "Firstname"),
		PICKUP_CS("Lastname", "Firstname");

		private List<String> params;

		private Type(String... params) {
			this.params = Arrays.asList(params);
		}

		public List<String> getParams() {
			return params;
		}
	}

	private String subject;

	private String title;

	private String body;

	@Enumerated(EnumType.STRING)
	private Type type;

	/**
	 * Returns the body.
	 * 
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 * 
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Returns the subject.
	 * 
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

}

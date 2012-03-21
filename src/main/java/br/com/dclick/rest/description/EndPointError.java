package br.com.dclick.rest.description;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author marcelofelix
 * 
 */
public class EndPointError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7288712350480183942L;
	private String label;
	private String description;
	private final Class<? extends Exception> error;

	/**
	 * @param error
	 */
	public EndPointError(final Class<? extends Exception> error) {
		super();
		this.error = error;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the error
	 */
	@JsonIgnore
	public Class<? extends Exception> getError() {
		return error;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Error [label=" + label + ", description=" + description + "]";
	}

}

package br.com.dclick.rest.doc.description.controller;

/**
 * @author marcelofelix
 * 
 */
public class GlobalError {

	private final String label;
	private final String description;

	/**
	 * @param label
	 * @param description
	 */
	public GlobalError(final String label, final String description) {
		super();
		this.label = label;
		this.description = description;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}

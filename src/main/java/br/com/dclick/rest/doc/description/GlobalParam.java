package br.com.dclick.rest.doc.description;

/**
 * 
 * @author marcelofelix
 * 
 */
public class GlobalParam {

	private final String name;
	private final String value;
	private final String description;

	/**
	 * @param name
	 * @param value
	 * @param description
	 */
	public GlobalParam(final String name, final String value, final String description) {
		super();
		this.name = name;
		this.value = value;
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}

package br.com.dclick.rest.doc.description;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Descrição de um parametro do EndPoint
 * 
 * @author marcelofelix
 * 
 */
public class Param implements Comparable<Param>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8443419220759791394L;
	private final String name;
	private final boolean required;
	private final boolean urlParam;
	private String description;
	private List<String> values = new ArrayList<String>();

	/**
	 * @param name
	 * @param required
	 * @param urlParam
	 */
	public Param(final String name, final boolean required, final boolean urlParam) {
		super();
		this.name = name;
		this.required = required;
		this.urlParam = urlParam;
	}

	/**
	 * @param name
	 * @param required
	 */
	public Param(final String name, final boolean required) {
		super();
		this.name = name;
		this.required = required;
		urlParam = false;
	}

	/**
	 * @return nome do parametro
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return parametro é obrigatório
	 */
	public boolean isRequired() {
		return required;
	}

	@Override
	public int compareTo(final Param o) {
		return name.compareTo(o.name);
	}

	/**
	 * @return se é um parametro de url
	 */
	public boolean isUrlParam() {
		return urlParam;
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
	 * @return the values
	 */
	@JsonProperty("value")
	@JsonSerialize(include = Inclusion.NON_EMPTY)
	public List<String> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(final List<String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "Parameter [name=" + name + ", required=" + required + ", urlParam=" + urlParam + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + (required ? 1231 : 1237);
		result = (prime * result) + (urlParam ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Param other = (Param) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (required != other.required) {
			return false;
		}
		if (urlParam != other.urlParam) {
			return false;
		}
		return true;
	}

}

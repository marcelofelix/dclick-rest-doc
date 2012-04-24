package br.com.dclick.rest.doc.description;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.dclick.rest.doc.description.controller.GlobalError;

/**
 * @author marcelofelix
 * 
 */
public class Config implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7364261242124040021L;
	private String url;
	private Map<String, Group> groups = new HashMap<String, Group>();
	private List<GlobalParam> globalParams = new ArrayList<GlobalParam>();
	private List<GlobalError> globalErrors = new ArrayList<GlobalError>();

	/**
	 * @param endPoint
	 */
	public void addEndPoint(final EndPoint endPoint) {
		if (!groups.containsKey(endPoint.getGroup())) {
			groups.put(endPoint.getGroup(), new Group(endPoint.getGroup()));
		}
		groups.get(endPoint.getGroup()).add(endPoint);
	}

	/**
	 * @return EndPoints agrupados
	 */
	public Collection<Group> getGroups() {
		return groups.values();
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return methodParam
	 */
	public String getMethodParam() {
		return "_method";
	}

	/**
	 * @return traditional serialization
	 */
	public boolean getTraditionalSerialization() {
		return true;
	}

	/**
	 * @return the globlaParams
	 */
	public List<GlobalParam> getGlobalParams() {
		return globalParams;
	}

	/**
	 * @param globalParams
	 *            the globlaParams to set
	 */
	public void setGlobalParams(final List<GlobalParam> globalParams) {
		this.globalParams = globalParams;
	}

	/**
	 * @return the globalErrors
	 */
	public List<GlobalError> getGlobalErrors() {
		return globalErrors;
	}

	/**
	 * @param globalErrors
	 *            the globalErrors to set
	 */
	public void setGlobalErrors(final List<GlobalError> globalErrors) {
		this.globalErrors = globalErrors;
	}

}

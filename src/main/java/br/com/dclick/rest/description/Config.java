package br.com.dclick.rest.description;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private List<Param> globlaParams = new ArrayList<Param>();

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
	public List<Param> getGloblaParams() {
		return globlaParams;
	}

	/**
	 * @param globlaParams
	 *            the globlaParams to set
	 */
	public void setGloblaParams(final List<Param> globlaParams) {
		this.globlaParams = globlaParams;
	}

}

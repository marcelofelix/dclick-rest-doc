package br.com.dclick.rest.description;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
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
	private Map<String, Group> groups = new HashMap<String, Group>();

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
}

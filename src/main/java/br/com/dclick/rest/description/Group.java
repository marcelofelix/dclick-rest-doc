package br.com.dclick.rest.description;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marcelofelix
 * 
 */
public class Group {

	private final String name;
	private final List<EndPoint> endPoints = new ArrayList<EndPoint>();

	/**
	 * @param name
	 */
	public Group(final String name) {
		super();
		this.name = name;
	}

	/**
	 * @param endPoint
	 */
	public void add(final EndPoint endPoint) {
		this.endPoints.add(endPoint);
	}

	/**
	 * @return EndPoints
	 */
	public List<EndPoint> getEndPoints() {
		return endPoints;
	}

	/**
	 * @return Group name
	 */
	public String getName() {
		return name;
	}
}

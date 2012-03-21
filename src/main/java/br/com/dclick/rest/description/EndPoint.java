package br.com.dclick.rest.description;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Descreve um serviço
 * 
 * @author marcelofelix
 * 
 */
public class EndPoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1587490022248372311L;
	private final List<String> urls = new ArrayList<String>();
	private final List<RequestMethod> methods = new ArrayList<RequestMethod>();
	private final List<Param> params = new ArrayList<Param>();
	private String descriptions;
	private String label;
	private String group;

	/**
	 * @return Lista de methods do EndPoint (GET, POST, DELETE, PUT)
	 */
	public List<RequestMethod> getMethods() {
		return Collections.unmodifiableList(methods);
	}

	/**
	 * @return Lista de urls validas para o EndPoint
	 */
	public List<String> getUrls() {
		return Collections.unmodifiableList(urls);
	}

	/**
	 * @param url
	 */
	public void addUrl(final String... url) {
		addUrl(Arrays.asList(url));
	}

	/**
	 * @param urls
	 */
	public void addUrl(final Collection<String> urls) {
		this.urls.addAll(urls);
	}

	/**
	 * @param methods
	 */
	public void addMethod(final RequestMethod... methods) {
		addMethod(Arrays.asList(methods));
	}

	/**
	 * @param methods
	 */
	public void addMethod(final Collection<RequestMethod> methods) {
		this.methods.addAll(methods);
	}

	/**
	 * @param param
	 */
	public void addParam(final Collection<Param> param) {
		this.params.addAll(param);
	}

	/**
	 * @return lista de parametros do EndPoint
	 */
	public List<Param> getParams() {
		Collections.sort(params);
		return Collections.unmodifiableList(params);
	}

	/**
	 * @return description
	 */
	public String getDescriptions() {
		return descriptions;
	}

	/**
	 * @param descriptions
	 */
	public void setDescriptions(final String descriptions) {
		this.descriptions = descriptions;
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
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(final String group) {
		this.group = group;
	}

}

package br.com.dclick.rest.description;

import java.util.List;

/**
 * @author marcelofelix
 * 
 */
public interface DocumentResolver {

	/**
	 * @return Descrição do EndPoint
	 */
	String getDescription();

	/**
	 * @return Grupo do EndPoint
	 */
	String getGroup();

	/**
	 * @return Label do EndPoint
	 */
	String getLabel();

	/**
	 * @param parameterName
	 * @return Descrição do parâmetro
	 */
	String getParameterDescription(String parameterName);

	/**
	 * @param parameterName
	 * @return valores validos para o parâmetro
	 */
	List<String> getParameterValues(String parameterName);

}

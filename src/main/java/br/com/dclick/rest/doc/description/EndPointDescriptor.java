package br.com.dclick.rest.doc.description;

import java.util.Collection;

/**
 * Essa interface define metodos que devem ser implementados por classes que
 * criam a descrição de um EndPoint
 * 
 * @author marcelofelix
 * 
 */
public interface EndPointDescriptor {

	/**
	 * Retorna uma lista de EndPoint existente na classe
	 * 
	 * @param controller
	 * @return Lista de EndPoint existentes na classe
	 */
	Collection<EndPoint> describe(Class<?> controller);
}

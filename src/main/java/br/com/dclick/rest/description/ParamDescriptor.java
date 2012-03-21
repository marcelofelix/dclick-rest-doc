package br.com.dclick.rest.description;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author marcelofelix
 * 
 */
public interface ParamDescriptor {

	/**
	 * @param method
	 * @return Lista de parametros encontrados no metodo
	 */
	List<Param> getParams(Method method);
}

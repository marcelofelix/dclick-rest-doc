package br.com.dclick.rest.doc.description;

import static java.util.Arrays.asList;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import static br.com.dclick.rest.doc.description.ParameterValueDescriptorFactory.getParameterValue;

/**
 * Identifica os parametros dos EndPoints olhando as annotations existentes
 * 
 * @author marcelofelix
 * 
 */
@Component
public class AnnotationParameterDescriptor implements ParamDescriptor {

	/**
	 * @param method
	 * @return Parametros do metodo
	 */
	public List<Param> getParams(final Method method) {
		List<Param> parameters = new ArrayList<Param>();
		parameters.addAll(getRequestParam(method));
		parameters.addAll(getPathVariable(method));
		parameters.addAll(getModelAtribute(method));
		//parameters.addAll(getRequestBody(method));
		return parameters;
	}

	/**
	 * @param method
	 * @return Parametros anotados com @RequestParam
	 */
	private List<Param> getRequestParam(final Method method) {
		List<Param> parameters = new ArrayList<Param>();
		Class<?>[] parameterTypes = method.getParameterTypes();
		int index = 0;
		for (Annotation[] p : method.getParameterAnnotations()) {
			Class<?> parameterType = parameterTypes[index++];
			for (Annotation a : p) {
				if (a instanceof RequestParam) {
					RequestParam requestParam = (RequestParam) a;
					Param param = new Param(requestParam.value(), requestParam.required());
					parameters.add(param);
					param.setValues(getParameterValue(parameterType));
				}
			}
		}
		return parameters;
	}

	/**
	 * @param method
	 * @return Parametros anotados com @PathVariable
	 */
	private List<Param> getPathVariable(final Method method) {
		List<Param> parameters = new ArrayList<Param>();
		Class<?>[] parameterTypes = method.getParameterTypes();
		int index = 0;
		for (Annotation[] p : method.getParameterAnnotations()) {
			Class<?> parameterType = parameterTypes[index++];
			for (Annotation a : p) {
				if (a instanceof PathVariable) {
					PathVariable pathVariable = (PathVariable) a;
					Param param = new Param(pathVariable.value(), true, true);
					parameters.add(param);
					param.setValues(getParameterValue(parameterType));
				}
			}
		}
		return parameters;
	}

	/**
	 * @param method
	 * @return Parametros anotados com @ModelAtribute
	 */
	private List<Param> getModelAtribute(final Method method) {
		List<Param> parameters = new ArrayList<Param>();
		Iterator<Class<?>> parameterTypes = asList(method.getParameterTypes()).iterator();
		for (Annotation[] p : method.getParameterAnnotations()) {
			Class<?> parameterType = parameterTypes.next();
			for (Annotation a : p) {
				if (a instanceof ModelAttribute) {
					for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(parameterType)) {
						if (!pd.getName().equalsIgnoreCase("class")) {
							Param param = new Param(pd.getName(), false);
							parameters.add(param);
							param.setValues(getParameterValue(pd.getPropertyType()));
						}
					}
				}
			}
		}
		return parameters;
	}
	
	/**
	 * @param method
	 * @return Parametros anotados com @RequestBody
	 */
	private Collection<? extends Param> getRequestBody(Method method) {
		List<Param> parameters = new ArrayList<Param>();
		Iterator<Class<?>> parameterTypes = asList(method.getParameterTypes()).iterator();
		for (Annotation[] p : method.getParameterAnnotations()) {
			Class<?> parameterType = parameterTypes.next();
			for (Annotation a : p) {
				if (a instanceof RequestBody) {
					for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(parameterType)) {
						if (!pd.getName().equalsIgnoreCase("class")) {
							Param param = new Param(pd.getName(), false);
							parameters.add(param);
							param.setValues(getParameterValue(pd.getPropertyType()));
						}
					}
				}
			}
		}
		return parameters;
	}
}
package br.com.dclick.rest.description;

import static java.util.Arrays.asList;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.dclick.rest.description.annotations.EndPointDescription;
import br.com.dclick.rest.description.annotations.EndPointParam;

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
		readParameterDescription(parameters, method);
		return parameters;
	}

	/**
	 * @param method
	 */
	private void readParameterDescription(final List<Param> parameters, final Method method) {
		EndPointDescription description = AnnotationUtils.findAnnotation(method, EndPointDescription.class);
		if (description != null) {
			for (EndPointParam pd : description.params()) {
				for (Param p : parameters) {
					if (p.getName().equals(pd.name())) {
						p.setDescription(pd.description());
						p.setValues(asList(pd.values()));
					}
				}

			}
		}
	}

	/**
	 * @param method
	 * @return Parametros anotados com @RequestParam
	 */
	private List<Param> getRequestParam(final Method method) {
		List<Param> parameters = new ArrayList<Param>();
		for (Annotation[] p : method.getParameterAnnotations()) {
			for (Annotation a : p) {
				if (a instanceof RequestParam) {
					RequestParam param = (RequestParam) a;
					parameters.add(new Param(param.value(), param.required()));
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
		for (Annotation[] p : method.getParameterAnnotations()) {
			for (Annotation a : p) {
				if (a instanceof PathVariable) {
					PathVariable pathVariable = (PathVariable) a;
					parameters.add(new Param(pathVariable.value(), true, true));
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
							parameters.add(new Param(pd.getName(), false));
						}
					}
				}
			}
		}
		return parameters;
	}
}

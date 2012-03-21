package br.com.dclick.rest.description;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;

import br.com.dclick.rest.description.annotations.EndPointDescription;
import br.com.dclick.rest.description.annotations.EndPointParam;

/**
 * Faz a documentação do EndPoint utilizando os valores existentes nas
 * annotations
 * 
 * @author marcelofelix
 * 
 */

public class DefaultDocumentResolver implements DocumentResolver {

	private final EndPointDescription annotation;

	/**
	 * @param method
	 */
	public DefaultDocumentResolver(final Method method) {
		super();
		this.annotation = AnnotationUtils.findAnnotation(method, EndPointDescription.class);
	}

	@Override
	public String getDescription() {
		return annotation == null ? null : annotation.description();
	}

	@Override
	public String getGroup() {
		return annotation == null ? null : annotation.group();
	}

	@Override
	public String getLabel() {
		return annotation == null ? null : annotation.label();
	}

	@Override
	public String getParameterDescription(final String parameterName) {
		if (annotation != null) {
			for (EndPointParam p : annotation.params()) {
				if (p.name().equals(parameterName)) {
					return p.description();
				}
			}
		}
		return null;
	}

	@Override
	public List<String> getParameterValues(final String parameterName) {
		if (annotation != null) {
			for (EndPointParam p : annotation.params()) {
				if (p.name().equals(parameterName)) {
					return Arrays.asList(p.values());
				}
			}
		}
		return null;
	}

}

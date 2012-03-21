package br.com.dclick.rest.description;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.context.MessageSource;

/**
 * 
 * @author marcelofelix
 * 
 */
public class ComposedDocumentResolver implements DocumentResolver {

	private final DocumentResolver defaultResolver;
	private final DocumentResolver i18nResolver;

	/**
	 * @param message
	 * @param method
	 */
	public ComposedDocumentResolver(final MessageSource message, final Method method) {
		super();
		this.defaultResolver = new DefaultDocumentResolver(method);
		i18nResolver = new I18NDocumentResolver(message, method);
	}

	@Override
	public String getDescription() {
		return "".equals(defaultResolver.getDescription()) ? i18nResolver.getDescription() : defaultResolver
				.getDescription();
	}

	@Override
	public String getGroup() {
		return "".equals(defaultResolver.getGroup()) ? i18nResolver.getGroup() : defaultResolver
				.getGroup();
	}

	@Override
	public String getLabel() {
		return "".equals(defaultResolver.getLabel()) ? i18nResolver.getLabel() : defaultResolver
				.getLabel();
	}

	@Override
	public String getParameterDescription(final String parameterName) {
		if (defaultResolver.getParameterDescription(parameterName) == null) {
			return i18nResolver.getParameterDescription(parameterName);
		} else {
			return defaultResolver.getParameterDescription(parameterName);
		}
	}

	@Override
	public List<String> getParameterValues(final String parameterName) {
		if (defaultResolver.getParameterDescription(parameterName) == null) {
			return i18nResolver.getParameterValues(parameterName);
		} else {
			return defaultResolver.getParameterValues(parameterName);
		}
	}

}

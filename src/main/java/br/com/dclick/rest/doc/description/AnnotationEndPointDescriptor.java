package br.com.dclick.rest.doc.description;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethodSelector;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.util.UrlPathHelper;

import br.com.dclick.rest.doc.description.annotations.Errors;

/**
 * Essa classe faz a identificação de EndPoint utilizando as annotations do
 * spring
 * 
 * @author marcelofelix
 * 
 */

@Component
public class AnnotationEndPointDescriptor implements EndPointDescriptor {

	@Autowired
	private ParamDescriptor parameterFactory;

	@Autowired
	private DocumentResolver documentResolver;

	@Override
	public Collection<EndPoint> describe(final Class<?> controller) {
		Collection<EndPoint> descriptions = new ArrayList<EndPoint>();
		if (AnnotationUtils.findAnnotation(controller, Controller.class) != null) {
			Set<Method> methods = HandlerMethodSelector.selectMethods(controller, new MethodFilter() {
				public boolean matches(final Method method) {
					return getMappingForMethod(method, controller) != null;
				}
			});
			for (Method m : methods) {
				RequestMappingInfo info = getMappingForMethod(m, controller);

				EndPoint endPoint = new EndPoint(m);
				endPoint.addUrl(info.getPatternsCondition().getPatterns());
				endPoint.addMethod(info.getMethodsCondition().getMethods());
				endPoint.addParam(parameterFactory.getParams(m));
				endPoint.addErrors(getErrors(m));
				documentResolver.document(endPoint);
				descriptions.add(endPoint);
			}
		}
		return descriptions;
	}

	/**
	 * @param method
	 * @return EndPoint's errors
	 */
	public List<EndPointError> getErrors(final Method method) {
		List<EndPointError> endPointErrors = new ArrayList<EndPointError>();
		Errors errors = AnnotationUtils.findAnnotation(method, Errors.class);
		if (errors != null) {
			for (Class<? extends Exception> ex : errors.value()) {
				endPointErrors.add(new EndPointError(ex));
			}
		}
		return endPointErrors;
	}

	/**
	 * @param method
	 * @param handlerType
	 * @return Informações do metodo que é um end point
	 */
	protected RequestMappingInfo getMappingForMethod(final Method method, final Class<?> handlerType) {
		RequestMappingInfo info = null;
		RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		if (methodAnnotation != null) {
			info = createRequestMappingInfo(methodAnnotation);
			RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
			if (typeAnnotation != null) {
				info = createRequestMappingInfo(typeAnnotation).combine(info);
			}
		}
		return info;
	}

	/**
	 * @param annotation
	 * @return RequestMappingInfo
	 */
	private RequestMappingInfo createRequestMappingInfo(final RequestMapping annotation) {
		return new RequestMappingInfo(new PatternsRequestCondition(annotation.value(), new UrlPathHelper(),
				new AntPathMatcher(), true, true), new RequestMethodsRequestCondition(annotation.method()),
				new ParamsRequestCondition(annotation.params()), new HeadersRequestCondition(annotation.headers()),
				new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),
				new ProducesRequestCondition(annotation.produces(), annotation.headers()), null);
	}
}

package br.com.dclick.rest.description;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
	private MessageSource messageSource;

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
				DocumentResolver documentResolver = new ComposedDocumentResolver(messageSource, m);
				RequestMappingInfo info = getMappingForMethod(m, controller);
				
				EndPoint endPoint = new EndPoint();
				endPoint.addUrl(info.getPatternsCondition().getPatterns());
				endPoint.addMethod(info.getMethodsCondition().getMethods());
				endPoint.addParam(parameterFactory.getParams(m));
				endPoint.setDescriptions(documentResolver.getDescription());
				endPoint.setGroup(documentResolver.getGroup());
				endPoint.setLabel(documentResolver.getLabel());
				for (Param p : endPoint.getParams()) {
					p.setDescription(documentResolver.getParameterDescription(p.getName()));
					p.setValues(documentResolver.getParameterValues(p.getName()));
				}
				descriptions.add(endPoint);
			}
		}
		return descriptions;
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

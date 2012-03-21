package br.com.dclick.rest.description;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;

import br.com.dclick.rest.description.annotations.EndPointDescription;
import br.com.dclick.rest.description.annotations.EndPointParam;

/**
 * 
 * Permite a internacionalização da documentação dos EndPoints
 * 
 * os codigos para internacionalização devem segui o seguinte padrão:
 * 
 * pacote.nomeClasse.nomeMetodo = descriçao do EndPoint
 * pacote.nomeClasse.nomeMetodo.grupo = grupo do EndPoint
 * pacote.nomeClasse.nomeMetodo.label = label do EndPoint
 * pacote.nomeClasse.nomeMetodo.parameter.nomeParametro = descrição do parametro
 * 
 * Exemplo:
 * 
 * package a.b.c
 * 
 * class Service { public void endPoint(@PathVariable("id") final Long id) {} }
 * 
 * 
 * a.b.c.Service.endPoint = descrição do EndPoint a.b.c.Service.endPoint.group =
 * grupo do EndPoint a.b.c.Service.endPoint.label = label do EndPoint
 * a.b.c.Service.endPoint.parameter.id = descrição do parâmetro id
 * 
 * @author marcelofelix
 * 
 */
public class I18NDocumentResolver implements DocumentResolver {

	private final MessageSource message;
	private final Method method;

	/**
	 * @param message
	 * @param method
	 */
	public I18NDocumentResolver(final MessageSource message, final Method method) {
		super();
		this.message = message;
		this.method = method;
	}

	@Override
	public String getDescription() {
		return getMessage(getDescriptionCode());
	}

	@Override
	public String getGroup() {
		return getMessage(getGroupCode());
	}

	@Override
	public String getLabel() {
		return getMessage(getLabelCode());
	}

	@Override
	public String getParameterDescription(final String parameterName) {
		return getMessage(getDescriptionCode() + ".parameter." + parameterName);
	}

	@Override
	public List<String> getParameterValues(final String parameterName) {
		EndPointDescription annotation = AnnotationUtils.findAnnotation(method, EndPointDescription.class);
		if (annotation != null) {
			for (EndPointParam p : annotation.params()) {
				if (p.name().equals(parameterName)) {
					return Arrays.asList(p.values());
				}
			}
		}
		return null;
	}

	/**
	 * @param code
	 * @return mensagem
	 */
	private String getMessage(final String code) {
		return this.message.getMessage(code, new Object[] {}, "", Locale.getDefault());
	}

	/**
	 * @return código base para internacionalização
	 */
	private String getClassName() {
		return this.method.getDeclaringClass().getName();
	}

	/**
	 * @return código para a descrição do EndPoint
	 */
	private String getDescriptionCode() {
		return getClassName() + "." + method.getName();
	}

	/**
	 * @return códiogo para o label do EndPoint
	 */
	private String getLabelCode() {
		return getDescriptionCode() + ".label";
	}

	/**
	 * @return código para o grupo do EndPoint
	 */
	private String getGroupCode() {
		return getDescriptionCode() + ".group";
	}
}

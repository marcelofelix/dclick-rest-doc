package br.com.dclick.rest.doc.description;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

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
@Component
public class I18NDocumentResolver implements DocumentResolver {

	@Autowired
	private ApplicationContext message;

	@Override
	public void document(final EndPoint endPoint) {
		endPoint.setDescriptions(getDescription(endPoint));
		endPoint.setGroup(getGroup(endPoint));
		endPoint.setLabel(getLabel(endPoint));
		for (Param p : endPoint.getParams()) {
			p.setDescription(getParameterDescription(endPoint, p.getName()));
			p.setValues(getParameterValues(endPoint, p.getName()));
		}
		for (EndPointError e : endPoint.getErrors()) {
			e.setLabel(getErrorLabel(endPoint, e.getError()));
			e.setDescription(getErrorDescription(endPoint, e.getError()));
		}
	}

	/**
	 * @return description
	 */
	private String getDescription(final EndPoint endPoint) {
		return getMessage(getDescriptionCode(endPoint));
	}

	/**
	 * @return group
	 */
	private String getGroup(final EndPoint endPoint) {
		return getMessage(getGroupCode(endPoint), endPoint.getDeclaringClass().getSimpleName());
	}

	/**
	 * @return label
	 */
	private String getLabel(final EndPoint endPoint) {
		return getMessage(getLabelCode(endPoint), endPoint.getMethod().getName());
	}

	/**
	 * @param parameterName
	 * @return parameter description
	 */
	private String getParameterDescription(final EndPoint endPoint, final String parameterName) {
		return getMessage(getDescriptionCode(endPoint) + ".$parameter." + parameterName);
	}

	/**
	 * @param parameterName
	 * @return parameter values
	 */
	private List<String> getParameterValues(final EndPoint endPoint, final String parameterName) {
		String value = getMessage(getDescriptionCode(endPoint) + ".$parameter." + parameterName + ".value");
		List<String> values = new ArrayList<String>();
		if (!value.trim().isEmpty()) {
			for (String v : value.split(",")) {
				values.add(v.trim());
			}
		}
		return values;
	}

	/**
	 * @param code
	 * @return mensagem
	 */
	private String getMessage(final String code) {
		return this.message.getMessage(code, new Object[] {}, "", Locale.getDefault());
	}

	/**
	 * @param code
	 * @param defaultMessage
	 * @return Message
	 */
	private String getMessage(final String code, final String defaultMessage) {
		return this.message.getMessage(code, new Object[] {}, defaultMessage, Locale.getDefault());
	}

	/**
	 * @return código para a descrição do EndPoint
	 */
	private String getDescriptionCode(final EndPoint endPoint) {
		return endPoint.getCode();
	}

	/**
	 * @return códiogo para o label do EndPoint
	 */
	private String getLabelCode(final EndPoint endPoint) {
		return getDescriptionCode(endPoint) + ".label";
	}

	/**
	 * @return código para o grupo do EndPoint
	 */
	private String getGroupCode(final EndPoint endPoint) {
		return getDescriptionCode(endPoint) + ".group";
	}

	/**
	 * @param error
	 * @return error description
	 */
	private String getErrorDescription(final EndPoint endPoint, final Class<? extends Exception> error) {
		String code = getDescriptionCode(endPoint) + ".$error." + error.getSimpleName() + ".description";
		return getMessage(code, error.getSimpleName());
	}

	/**
	 * @param error
	 * @return label error
	 */
	private String getErrorLabel(final EndPoint endPoint, final Class<? extends Exception> error) {
		String code = getDescriptionCode(endPoint) + ".$error." + error.getSimpleName() + ".label";
		return getMessage(code, error.getName());
	}
}

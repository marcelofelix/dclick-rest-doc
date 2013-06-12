package br.com.dclick.rest.doc.description.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.dclick.rest.doc.description.Config;
import br.com.dclick.rest.doc.description.EndPoint;
import br.com.dclick.rest.doc.description.EndPointDescriptor;
import br.com.dclick.rest.doc.description.GlobalParam;

/**
 * @author marcelofelix
 * 
 */
@Controller
@RequestMapping(value = "/doc")
public class DocController {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private EndPointDescriptor descriptor;

	private List<GlobalParam> globalParams = new ArrayList<GlobalParam>();
	private List<GlobalError> globalErrors = new ArrayList<GlobalError>();

	/**
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	@PostConstruct
	private void createGlobalParams() {
		if (context.containsBean("globalParamsCode")) {
			List<String> globalParamsCode = (List<String>) context.getBean("globalParamsCode");
			for (String code : globalParamsCode) {
				String name = context.getMessage(code.concat(".").concat("name"), null, "", Locale.getDefault());
				String value = context
						.getMessage(code.concat(".").concat("value"), null, "", Locale.getDefault());
				String description = context.getMessage(code.concat(".").concat("description"), null, "",
						Locale.getDefault());
				globalParams.add(new GlobalParam(name, value, description));
			}
		}
		if (context.containsBean("globalErrorsCode")) {
			List<String> globalErrorsCode = (List<String>) context.getBean("globalErrorsCode");
			for (String code : globalErrorsCode) {
				String label = context
						.getMessage(code.concat(".").concat("label"), null, "", Locale.getDefault());
				String description = context.getMessage(code.concat(".").concat("description"), null, "",
						Locale.getDefault());
				globalErrors.add(new GlobalError(label, description));
			}
		}

	}

	/**
	 * @return configuração dos EndPoints
	 */
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public Config gethelp() {
		Config config = new Config();
		String[] names = context.getBeanNamesForType(Object.class);
		for (String name : names) {
			Class<?> type = context.getType(name);
			final Class<?> userType = ClassUtils.getUserClass(type);
			for (EndPoint e : descriptor.describe(userType)) {
				config.addEndPoint(e);
			}

		}
		config.setGlobalParams(globalParams);
		config.setGlobalErrors(globalErrors);
		return config;
	}

}

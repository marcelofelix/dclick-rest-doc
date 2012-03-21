package br.com.dclick.rest.description.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.dclick.rest.description.Config;
import br.com.dclick.rest.description.EndPoint;
import br.com.dclick.rest.description.EndPointDescriptor;

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
		return config;
	}

}

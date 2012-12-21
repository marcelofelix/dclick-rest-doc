package br.com.dclick.rest.description;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.dclick.rest.doc.description.EndPoint;
import br.com.dclick.rest.doc.description.EndPointDescriptor;
import br.com.dclick.rest.doc.description.EndPointError;
import br.com.dclick.rest.doc.description.Param;
import br.com.dclick.rest.doc.description.annotations.EndPointCode;
import br.com.dclick.rest.doc.description.annotations.Errors;

/**
 * 
 * @author marcelofelix
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AnnotationEndPointDescriptorTest {

	@Autowired
	private EndPointDescriptor descriptor;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		Locale.setDefault(Locale.ENGLISH);
	}

	/***/
	@Controller
	static class MethodsControllerAnnotated {

		/***/
		@RequestMapping(value = "/login", method = { GET })
		public void method1() {
		};

		/***/
		@RequestMapping(value = "/logout", method = { GET })
		public void method2() {
		};
	}

	/**
	 * Testa total de EndPoints encontrados
	 */
	@Test
	public void testTotalEndPointsRecognazed() {
		Collection<EndPoint> descriptions = descriptor.describe(MethodsControllerAnnotated.class);
		assertNotNull("Amount of EndPoints recognazed", descriptions);
		assertEquals("Total de EndPoints encontrados", 2, descriptions.size());
	}

	/**
	 * Testa as urls dos EndPoints encontrados
	 */
	@Test
	public void testUrlOfMethodsAnnotated() {
		Collection<String> urls = asList("/login", "/logout");
		for (EndPoint s : descriptor.describe(MethodsControllerAnnotated.class)) {
			assertTrue("EndPoint encontrado", urls.containsAll(s.getUrls()));
		}

	}

	/***/
	@Controller
	@RequestMapping(value = "/login")
	static class ControllerAnnotated {

		/***/
		public void login() {
		};

	}

	/**
	 * 
	 */
	@Test
	public void testControllerWithoutEndPoint() {
		assertTrue("Nenhum end point encontrado", descriptor.describe(ControllerAnnotated.class)
				.isEmpty());
	}

	/***/
	@Controller
	@RequestMapping(value = { "/user", "/usr" })
	static class EndPointWithMultipleUrls {
		/***/
		@RequestMapping(value = "/login", method = { GET, POST })
		public void method() {
		};

	}

	/***/
	@Controller
	static class EndPointWithRequestParameter {

		/***/
		@RequestMapping(value = "/login", method = { GET, POST })
		public void method(@RequestParam("name") final String name) {
		};

	}

	/**
	 * Testa quantidade de parametros anotados com @RequestPara
	 */
	@Test
	public void testRecognizeRequestParameterFromEndPoint() {
		EndPoint endPoint = descriptor.describe(EndPointWithRequestParameter.class).iterator().next();
		assertEquals("Amount of request parameter", 1, endPoint.getParams().size());
		Param param = endPoint.getParams().get(0);
		assertEquals("Parameter name", "name", param.getName());
		assertTrue("Parameter required", param.isRequired());
	}

	/***/
	@Controller
	static class EndPointWithModelAtributeAsParam {
		/***/
		static class User {
			private String name;
			private int age;

			/** @return String */
			public String getName() {
				return name;
			}

			/***/
			public void setName(final String name) {
				this.name = name;
			}

			/** @return int */
			public int getAge() {
				return age;
			}

			/***/
			public void setAge(final int age) {
				this.age = age;
			}

		}

		/***/
		@RequestMapping
		public void method(@ModelAttribute final User user) {
		};
	}

	/**
	 * Testa quantidade de parametros anotados com @ModelAtribute
	 */
	@Test
	public void testRecognizeModelAtributeParameterFromEndPoint() {
		List<Param> expectedParameters = new ArrayList<Param>();
		expectedParameters.add(new Param("age", false));
		expectedParameters.add(new Param("name", false));

		EndPoint endPoint = descriptor.describe(EndPointWithModelAtributeAsParam.class).iterator()
				.next();
		assertEquals("Amount of request parameter", 2, endPoint.getParams().size());
		assertEquals("Parameters", expectedParameters, endPoint.getParams());
	}

	/***/
	@Controller
	static class EndPointWithRequestParamAndModelAtributeAsParam {
		/***/
		static class User {
			private String name;
			private int age;

			/** @return name */
			public String getName() {
				return name;
			}

			/** */
			public void setName(final String name) {
				this.name = name;
			}

			/** @return age */
			public int getAge() {
				return age;
			}

			/***/
			public void setAge(final int age) {
				this.age = age;
			}

		}

		/***/
		@RequestMapping
		public void method(@RequestParam("locale") final String locale, @ModelAttribute final User user) {
		};
	}

	/**
	 * Testa parametros anotados com @RequestParam e @ModelAtribute
	 * */
	@Test
	public void testRecognizeRequestParamAndModelAtributeParameterFromEndPoint() {
		List<Param> expectedParameters = new ArrayList<Param>();
		expectedParameters.add(new Param("age", false));
		expectedParameters.add(new Param("locale", true));
		expectedParameters.add(new Param("name", false));

		EndPoint endPoint = descriptor.describe(EndPointWithRequestParamAndModelAtributeAsParam.class)
				.iterator().next();
		assertEquals("Amount of request parameter", 3, endPoint.getParams().size());
		assertEquals("Parameters", expectedParameters, endPoint.getParams());
	}

	/***/
	@Controller
	static class EndPointWithPathVariable {
		/***/
		@RequestMapping("/user/{id}/detail")
		public void method(@PathVariable("id") final Long id) {
		}
	}

	/**
	 * Testa parametros anotados com @PathVariable
	 */
	@Test
	public void testRecoginizaEndPointWithPathVariable() {

		EndPoint endPoint = descriptor.describe(EndPointWithPathVariable.class).iterator().next();
		assertEquals("Amount of request parameter", 1, endPoint.getParams().size());
		assertEquals("Parameters", asList(new Param("id", true, true)), endPoint.getParams());
	}

	/***/
	@Controller
	static class EndPointWithDescriptionI18N {
		/***/
		@RequestMapping("/user/{id}/detail")
		public void method(@PathVariable(value = "id") final Long id) {

		}
	}

	/**
	 * 
	 */
	@Test
	public void testGetEndPointsDescriptionI18N() {
		Locale.setDefault(Locale.ENGLISH);

		EndPoint endPoint = descriptor.describe(EndPointWithDescriptionI18N.class).iterator().next();
		assertEquals("EndPoint's description", "Description of this endPoint", endPoint.getDescription());
		assertEquals("EndPoint's label", "Method", endPoint.getLabel());
		assertEquals("EndPoint's group", "Test", endPoint.getGroup());
		assertEquals("Parameter's description", "id's description", endPoint.getParams().get(0).getDescription());
		assertEquals("Parameter value", asList("1", "2"), endPoint.getParams().get(0).getValues());
	}

	/***/
	@Controller
	static class EndPointWithEndPointException {
		/***/
		@RequestMapping("/url")
		@Errors(RuntimeException.class)
		public void method(@RequestParam("id") final long id, @RequestParam("name") final String name) {

		}

	}

	/**
	 * 
	 */
	@Test
	public void testGetExceptionOfEndPoint() {
		Locale.setDefault(Locale.ENGLISH);
		EndPoint endPoint = descriptor
				.describe(EndPointWithEndPointException.class).iterator().next();
		EndPointError error = endPoint.getErrors().iterator().next();
		assertEquals("Error label", "Runtime label", error.getLabel());
		assertEquals("Error description", "Runtime description", error.getDescription());
	}

	/***/
	@Controller
	static class EndPointMethodCustomCode {
		/***/
		@RequestMapping("/url")
		@EndPointCode("user.method")
		public void method(@RequestParam("id") final long id, @RequestParam("name") final String name) {

		}

	}

	/**
	 * 
	 */
	@Test
	public void testEndPonintMethodeCustomCode() {
		Locale.setDefault(Locale.ENGLISH);
		EndPoint endPoint = descriptor
				.describe(EndPointMethodCustomCode.class).iterator().next();
		assertEquals("Description", "Custom code", endPoint.getDescription());
		assertEquals("Label", "Custom code label", endPoint.getLabel());
		assertEquals("Group", "Custom code group", endPoint.getGroup());
	}

	/***/
	@Controller
	@EndPointCode("sistem")
	static class EndPointComposedCustomCode {
		/***/
		@RequestMapping("/url")
		@EndPointCode("user.method")
		public void method(@RequestParam("id") final long id, @RequestParam("name") final String name) {

		}

	}

	/**
	 * 
	 */
	@Test
	public void testEndPonintComposedCustomCode() {
		Locale.setDefault(Locale.ENGLISH);
		EndPoint endPoint = descriptor
				.describe(EndPointComposedCustomCode.class).iterator().next();
		assertEquals("Description", "Composed custom code", endPoint.getDescription());
		assertEquals("Label", "Composed custom code label", endPoint.getLabel());
		assertEquals("Group", "Composed custom code group", endPoint.getGroup());
	}

}

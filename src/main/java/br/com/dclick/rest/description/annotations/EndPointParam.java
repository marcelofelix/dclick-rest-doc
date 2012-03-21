package br.com.dclick.rest.description.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author marcelofelix
 * 
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EndPointParam {

	/**
	 * Parameter name
	 */
	String name() default "";

	/**
	 * Parameter description
	 */
	String description() default "";

	/**
	 * List of valid values
	 */
	String[] values() default {};

}

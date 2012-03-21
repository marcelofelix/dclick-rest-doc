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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EndPointDescription {

	/**
	 * EndPoint's description
	 */
	String description() default "";

	/**
	 * label
	 */
	String label() default "";

	/**
	 * group
	 */
	String group() default "";

	/**
	 * Parameter's description
	 */
	EndPointParam[] params() default {};

}

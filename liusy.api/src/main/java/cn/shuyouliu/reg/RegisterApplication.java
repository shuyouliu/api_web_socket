package cn.shuyouliu.reg;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import cn.shuyouliu.liusy.action.IndexController;

/**
 * Registers the components to be used by the JAX-RS application
 * 
 * @author ama
 *
 */
public class RegisterApplication extends ResourceConfig {

	/**
	 * Register JAX-RS application components.
	 */
	public RegisterApplication() {
		register(RequestContextFilter.class);
		register(IndexController.class);
		register(JacksonFeature.class);

	}

}

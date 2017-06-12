package com.leoni.forsimport.services;

import java.io.IOException;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.ImportModule;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import com.leoni.forsimport.dal.DataModule;
import com.leoni.forsimport.dal.HibernateModule;

/**
 * This module is automatically included as part of the Tapestry IoC Registry,
 * it's a good place to configure and extend Tapestry, or to place your own
 * service definitions.
 */
@ImportModule(
        { HibernateModule.class, // DAO layer
            DataModule.class     // Demo data loading
        })
public class AppModule {
	

//	 @Contribute(SymbolProvider.class)
//	    @FactoryDefaults
//	    public static void provideFactoryDefaults(final MappedConfiguration<String, String> configuration) {
//	        configuration.add(JpaSymbols.PERSISTENCE_DESCRIPTOR, "/forsimport/src/main/webapp/WEB-INF/persistence.xml");
//	    }
	 
	public static void bind(ServiceBinder binder) {
		binder.bind(TabNames.class);
		binder.bind(Authenticator.class, BasicAuthenticator.class);
		// binder.bind(MyServiceInterface.class, MyServiceImpl.class);

		// Make bind() calls on the binder object to define most IoC services.
		// Use service builder methods (example below) when the implementation
		// is provided inline, or requires more initialization than simply
		// invoking the constructor.
	}

	public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
		configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
		configuration.override(SymbolConstants.PRODUCTION_MODE, false);
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "changed");
	}

	/**
	 * Use annotation or method naming convention:
	 * <code>contributeApplicationDefaults</code>
	 */
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void setupEnvironment(MappedConfiguration<String, Object> configuration) {
		// Support for jQuery is new in Tapestry 5.4 and will become the only
		// supported
		// option in 5.5.
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
		configuration.add(SymbolConstants.BOOTSTRAP_ROOT, "context:mybootstrap");
		configuration.add(SymbolConstants.MINIFICATION_ENABLED, true);
		configuration.add(SymbolConstants.COMPRESS_WHITESPACE, false);
		configuration.add(SymbolConstants.START_PAGE_NAME, "com.leoni.forsimport.pages.Import");
	}

	/**
	 * This is a service definition, the service will be named "TimingFilter".
	 * The interface, RequestFilter, is used within the RequestHandler service
	 * pipeline, which is built from the RequestHandler service configuration.
	 * Tapestry IoC is responsible for passing in an appropriate Logger
	 * instance. Requests for static resources are handled at a higher level, so
	 * this filter will only be invoked for Tapestry related requests.
	 *
	 *
	 * Service builder methods are useful when the implementation is inline as
	 * an inner class (as here) or require some other kind of special
	 * initialization. In most cases, use the static bind() method instead.
	 *
	 *
	 * If this method was named "build", then the service id would be taken from
	 * the service interface and would be "RequestFilter". Since Tapestry
	 * already defines a service named "RequestFilter" we use an explicit
	 * service id that we can reference inside the contribution method.
	 */
	public RequestFilter buildTimingFilter(final Logger log) {
		return new RequestFilter() {
			@Override
			public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
				try {
					return handler.service(request, response);
				} finally {
				}
			}
		};
	}

	/**
	 * This is a contribution to the RequestHandler service configuration. This
	 * is how we extend Tapestry using the timing filter. A common use for this
	 * kind of filter is transaction management or security. The @Local
	 * annotation selects the desired service by type, but only from the same
	 * module. Without @Local, there would be an error due to the other
	 * service(s) that implement RequestFilter (defined in other modules).
	 */
	@Contribute(RequestHandler.class)
	public void addTimingFilter(OrderedConfiguration<RequestFilter> configuration, @Local RequestFilter filter) {
		configuration.add("Timing", filter);
	}
}

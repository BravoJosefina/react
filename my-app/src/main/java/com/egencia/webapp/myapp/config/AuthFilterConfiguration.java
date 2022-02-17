package com.egencia.webapp.myapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.egencia.service.library.AbstractAuthFilterConfiguration;

/**
 * Configuration of the AuthFilter component.
 * This essentially replaces the springsecurity-context.xml
 *
 * @author aallegret
 */
@Configuration
@EnableWebSecurity
public class AuthFilterConfiguration extends AbstractAuthFilterConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(AuthFilterConfiguration.class);

	/** {@inheritDoc} */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (!environment.getProperty("auth.enabled", boolean.class, true)) {
			logger.info("AuthFilter disabled via the property value \"auth.enabled=false\".");
			http.csrf().disable();
			return;
		}
		logger.info("Configuring AuthFilter...");

		http.csrf().disable()
				.addFilterBefore(tokenAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
				.exceptionHandling()
				.authenticationEntryPoint(tokenAuthenticationEntryPoint())
				.and()
				.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/base/**", "/api-docs/**").permitAll() // Public API
					.anyRequest().fullyAuthenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		logger.info("AuthFilter configuration completed.");
	}

	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
		super.configure(web);
	}

}

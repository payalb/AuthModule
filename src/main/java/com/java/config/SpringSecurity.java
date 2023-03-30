package com.java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity {

	@Autowired
	JwtAuthenticationFilter filter;
	@Autowired
	JwtUserDetailsService jwtUserDetailsService;
	@Autowired
	JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(jwtUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());

		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	/*
	 * @Bean public AuthenticationManager authenticationManager() { return new
	 * ProviderManager(Collections.singletonList(daoAuthenticationProvider())); }
	 */


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and()
		.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authenticationProvider(daoAuthenticationProvider());
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests()
        .antMatchers(HttpMethod.POST, "/v1/users/login").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated();
		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> platformCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration configAutenticacao = new CorsConfiguration();
		configAutenticacao.setAllowCredentials(true);
		// Must set it explicitly to allow frontend domain
		configAutenticacao.addAllowedOrigin("*");
		configAutenticacao.addAllowedHeader("Authorization");
		configAutenticacao.addAllowedHeader("Content-Type");
		configAutenticacao.addAllowedHeader("Accept");
		configAutenticacao.addAllowedMethod("POST");
		configAutenticacao.addAllowedMethod("GET");
		configAutenticacao.addAllowedMethod("DELETE");
		configAutenticacao.addAllowedMethod("PUT");
		configAutenticacao.addAllowedMethod("OPTIONS");
		configAutenticacao.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", configAutenticacao);

		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(-110);
		return bean;
	}
}

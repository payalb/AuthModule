package com.java.config;



import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurity {

   


	/*
	 * @Autowired public void globalUserDetails(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * auth.userDetailsService(jwtUserDetailsService).passwordEncoder(
	 * passwordEncoder()); }
	 */
	/*
	 * @Bean public JwtAuthenticationFilter authenticationTokenFilterBean() throws
	 * Exception { return new JwtAuthenticationFilter(); }
	 */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/**").permitAll()
              //  .anyRequest().authenticated()
                .and()
             //   .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
     //   http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return encoder;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> platformCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration configAutenticacao = new CorsConfiguration();
        configAutenticacao.setAllowCredentials(true);
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

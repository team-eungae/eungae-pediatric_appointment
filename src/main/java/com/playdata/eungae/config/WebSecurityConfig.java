package com.playdata.eungae.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.playdata.eungae.security.MemberUserDetailsService;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

	private final MemberUserDetailsService memberUserDetailsService;

	// 시큐리티 비활성화
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
			.requestMatchers(new AntPathRequestMatcher("/img/**"))
			.requestMatchers(new AntPathRequestMatcher("/css/**"))
			.requestMatchers(new AntPathRequestMatcher("/js/**"))
			.requestMatchers(new AntPathRequestMatcher("/img/**"))
			.requestMatchers(new AntPathRequestMatcher("/lib/**"));
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws
		Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorizationRequests -> authorizationRequests
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/login")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/signup")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/map")).permitAll()
				.anyRequest().permitAll()
			)
			.formLogin(login -> login
				.loginPage("/login")
				.loginProcessingUrl("/login-proc")
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/")
				.failureUrl("/login/error"))
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/"));
		return http.build();
	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

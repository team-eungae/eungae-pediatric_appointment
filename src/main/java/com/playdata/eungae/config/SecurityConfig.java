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

import com.playdata.eungae.security.MemberOAuthUserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final MemberOAuthUserService memberOAuthUserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws
		Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(new MvcRequestMatcher(introspector, "/")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/api/**")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/login/oauth2/code/kakao")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/login")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/signup")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/map")).permitAll()
				.anyRequest().authenticated())
			.formLogin(login -> login
				.loginPage("/login")
				.loginProcessingUrl("/login-proc")
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login/error"))
			.oauth2Login(oauth -> oauth
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.failureUrl("/login")
				.userInfoEndpoint(userInfo -> userInfo
					.userService(memberOAuthUserService)))
			.logout(logout -> logout
			.logoutSuccessUrl("/")
			.logoutUrl("/logout"))
			.build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web -> web.ignoring()
			.requestMatchers(new AntPathRequestMatcher("/img/**"))
			.requestMatchers(new AntPathRequestMatcher("/css/**"))
			.requestMatchers(new AntPathRequestMatcher("/js/**"))
			.requestMatchers(new AntPathRequestMatcher("/img/**"))
			.requestMatchers(new AntPathRequestMatcher("/images/**"))
			.requestMatchers(new AntPathRequestMatcher("/lib/**"))
			.requestMatchers(new AntPathRequestMatcher("/error"))
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**")));
	}
}

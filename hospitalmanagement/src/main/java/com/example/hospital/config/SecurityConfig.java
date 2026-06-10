package com.example.hospital.config;

import com.example.hospital.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/ai/ask", "/contact/submit", "/patient/review/add"))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login", "/register/**", "/css/**", "/js/**", "/images/**",
								"/doctor_list", "/doctors", "/healthTips", "/api/**", "/api/ai/ask", "/contact/submit",
								"/patient/notice/**", "/favicon.ico")
						.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/doctor/**")
						.hasRole("DOCTOR").requestMatchers("/patient/**").hasRole("PATIENT").anyRequest()
						.authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/default", true).permitAll())
				.logout(logout -> logout.logoutUrl("/logout") // 保持默认
						.logoutSuccessUrl("/login?logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
						.permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // 允许GET方式退出
				).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}

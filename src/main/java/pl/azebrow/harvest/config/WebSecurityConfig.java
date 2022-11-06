package pl.azebrow.harvest.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
@RequiredArgsConstructor
@EnableJpaAuditing
@Slf4j
public class WebSecurityConfig {

    @SuppressWarnings("unused")
    private final UserDetailsService userDetailsService;

    @Value("${harvest.remember-me.key:}")
    private String rememberMeKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers("/api/v1/recovery/**").anonymous()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated().and()
                .rememberMe(this::configureRememberMe)
                .formLogin(this::configureLogin)
                .logout(this::configureLogout)
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        return http.build();
    }

    private void configureLogout(LogoutConfigurer<HttpSecurity> configurer) {
        configurer
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void configureLogin(FormLoginConfigurer<HttpSecurity> configurer) {
        configurer
                .loginPage("/login").permitAll()
                .successHandler((_request, _response, _auth) -> System.out.println("do nuffin, success"))
                .failureHandler((_request, _response, _auth) -> _response.setStatus(HttpServletResponse.SC_UNAUTHORIZED));
    }

    private void configureRememberMe(RememberMeConfigurer<HttpSecurity> configurer) {
        if (rememberMeKey.isEmpty()) {
            log.warn("Using generated remember-me key");
            return;
        }
        configurer.key(rememberMeKey);
        log.info("Using predefined remember-me key");
    }
}

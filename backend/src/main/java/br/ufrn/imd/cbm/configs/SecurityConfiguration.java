package br.ufrn.imd.cbm.configs;

import br.ufrn.imd.cbm.filters.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    // Endpoints públicos
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users/login",
            "/users"
    };

    // Endpoints funcionais para qualquer usuário autenticado
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/users/test"
    };

    // Endpoints que só podem ser acessado por usuários com permissão de cliente
    public static final String [] ENDPOINTS_CUSTOMER = {
            "/users/test/customer",
            "/users/{id}/address/",
            "/users/{id}/address/{address}"
    };

    // Endpoints que só podem ser acessado por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMIN = {
            "/users/test/administrator",
            "/users/{id}",
            "/users/{id}/address/**",
            "/address/**",
            "/users/find-by-email",
            "/transactions/**",
            "/items/**",
            "/recipes/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests() // Habilita a autorização para as requisições HTTP
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                .anyRequest().denyAll()
                .and().addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
package ec.com.corebank.banquito.Auth;


import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;





@Configuration
@EnableWebSecurity
public class SpringSecurityConfig{



    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       
        http    
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll()) // Permite acceso sin autenticación a todas las rutas
                .httpBasic(Customizer.withDefaults()); // Configura autenticación básica HTTP (opcional, puedes eliminarlo si no lo necesitas)

        return http.build();
    }
   

    @Bean
    CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        // config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT","OPTIONS"));
        // config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        
    }

    
}

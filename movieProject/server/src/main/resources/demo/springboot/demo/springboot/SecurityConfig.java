package demo.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
	@Bean
 	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
 	{
         http
                 .csrf((csrf) -> csrf.disable())
                 .authorizeHttpRequests((requests) -> requests
                		 		 .requestMatchers( "/resources/**", "/", "/index.html", "/hello.html").permitAll()
                                 .requestMatchers("/admin").hasRole("ADMIN")
                                 .requestMatchers("/user").hasRole("USER")
                 )
                 .httpBasic(Customizer.withDefaults());
                 
 		return http.build();
 	}

}

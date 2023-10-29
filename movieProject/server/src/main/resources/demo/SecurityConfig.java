package demo.springboot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

//import demo.springboot.filter.CorsFilter;
import demo.springboot.filter.JwtTokenFilter;
import demo.springboot.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
	
	public SecurityConfig() {
		super();
		System.out.println("In SecurityConfig constructor");
	}

	@Autowired
	JwtTokenFilter jwtFilter;
/*	
	@Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }
*/    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors((c)-> {
        	CorsConfigurationSource source = request -> {
        		CorsConfiguration config = new CorsConfiguration();
        		//config.
        		config.setAllowedOrigins(
        				List.of("http://localhost:4200"));
        		config.setAllowedMethods(
        				List.of("GET", "POST", "PUT", "DELETE"));
        		return config;		       		
        	};
        	c.configurationSource(source);
        });
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests((authz) -> authz
//                .requestMatchers("/").permitAll()
//                .requestMatchers(toH2Console()).permitAll()     
                .anyRequest().permitAll());//authenticated());
        
        http.sessionManagement((session) ->  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(corsFilter(), SessionManagementFilter.class); //adds your custom CorsFilter
        
        http.headers(header -> header.frameOptions(config -> config.sameOrigin()));
        return http.build();
    }
/*    
    @Bean
    public WebSecurityCustomizer apiStaticResources() {
        return (web) -> web.ignoring()
        		.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        		.and().ignoring()
        		.requestMatchers("/*.html");
    }
*/
    
 /*   
    @Bean
    public CorsConfigurationSource corsConfiguration() {
    	System.out.println("In corsConfiguration()");
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("PATCH");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("OPTIONS");
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
        corsConfig.setExposedHeaders(Arrays.asList("X-Get-Header"));
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
*/
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
}

package demo.springboot.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import demo.springboot.filter.JwtTokenFilter;
import demo.springboot.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	private final String ORIGIN_1 = "http://localhost:4200";
	private final String LOGOUT_URL="/signout";
	
	@Autowired
	JwtTokenFilter jwtFilter;
	
	public SecurityConfig() {
		super();
		System.out.println("In SecurityConfig constructor");
	}

	/*
	 * 
	 *         http.authorizeHttpRequests((req) -> req
        		.requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
        		.requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
        		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        		.requestMatchers(new AntPathRequestMatcher("/**"))
        		.authenticated())
                //.anyRequest().authenticated()
                .csrf(c->c.disable())
                .formLogin(f->f.disable())
                .sessionManagement((session) ->  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(header -> header.frameOptions(config -> config.sameOrigin()));

	 * 
	 * 
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
System.out.println("In securityFilterChain");	
        http.authorizeHttpRequests((req) -> req
        		.requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
        		.requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
        		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        		//.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
        		.anyRequest()
                .authenticated())
                .logout((l)->l
                		//.logoutUrl(LOGOUT_URL)
                		.invalidateHttpSession(true)
                		.deleteCookies("JSESSIONID"));
//                		.clearAuthentication(true));
                
        http
                .csrf(c->c.disable())
                //.cors(c->c.disable())
                .formLogin(f->f.disable())
                .sessionManagement((session) ->  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(header -> header.frameOptions(config -> config.sameOrigin()));
                
                
        		//.headers(header -> header.frameOptions(config -> config.sameOrigin()));
        return http.build();	
    }
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

/*

// This code works, but is not necessary?
// insert this if u get CORS error 
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
System.out.println("In corsConfigurationSource");    	
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(Arrays.asList(ORIGIN_1));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

// --- copy till above 

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.cors((c)-> {
    	CorsConfigurationSource source = request -> {
    		CorsConfiguration config = new CorsConfiguration();
    		config.setAllowedOrigins(
    				List.of("http://localhost:4200"));
    		config.setAllowedMethods(
    				List.of("GET", "POST", "PUT", "DELETE"));
    		return config;		       		
    	};
    	c.configurationSource(source);
    });

	
	http.
	csrf()
	.disable()
	.authorizeRequests()
	.antMatchers("/signin").permitAll().and()
    .authorizeRequests()
    .requestMatchers(req -> (req.getRequestURI().contains("B") && !req.getRequestURI().contains("delete") && !req.getMethod().equals(HttpMethod.DELETE.toString())
            || (req.getMethod().equals(HttpMethod.GET.toString())
                    && req.getRequestURI().contains("A"))))
    .hasAnyAuthority("editor", "admin").and().authorizeRequests()
    .requestMatchers(req -> (req.getMethod().equals(HttpMethod.GET.toString())
            && req.getRequestURI().equals("/A")))
    .hasAnyAuthority("user", "editor", "admin")
    .and().authorizeRequests().anyRequest()
    .hasAuthority("admin").and()                
    .sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

httpSecurity.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);


*/

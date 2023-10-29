package demo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages={"demo.springboot"})
public class DemoMovieApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DemoMovieApplication.class, args);
	}
}

/*	
	private final String ORIGIN_1 = "http://localhost:4200";
	private final String ORIGIN_2 = "http://localhost:4201";
	private final String ORIGIN_3 = "http://localhost:4202";
	
	@Value("${allowed.origin")
	private String allowedOrigin;
	
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
System.out.println("In main(), corsConfigurer()");		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry)
			{
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						//.allowedOrigins(allowedOrigin)
						//.allowedOrigins(ORIGIN_2, ORIGIN_3)
						//.allowedOrigins("*")
						.allowedMethods("POST", "PUT", "GET", "DELETE")
						//.allowedMethods("*")
						.allowedHeaders("*");
			}
		};		
	}
	
*/		



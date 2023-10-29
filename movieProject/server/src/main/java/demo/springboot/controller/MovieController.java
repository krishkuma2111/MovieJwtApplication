package demo.springboot.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.springboot.model.Movie;
import demo.springboot.service.MovieServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class MovieController 
{
	@Autowired
	private MovieServiceImpl movieService;

	public MovieController() {		
		super();
		System.out.println("In MovieController");
	}

//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")

	@GetMapping("/movies/list")
	public ResponseEntity<List<Movie>> getMovies()//@RequestHeader(name="Authorization") String token)
	{
		System.out.println("In findAll user: " + getUsername());		
		return movieService.findAll();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/movies/insert")
	public ResponseEntity<Movie> insert(@RequestBody Movie movie, @RequestHeader(name="Authorization") String token)
	{
		System.out.println("In insert user: " + getUsername());
		return movieService.save(movie);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/movies/update")
	public ResponseEntity<Movie> update(@RequestBody Movie movie)
	{
		System.out.println("In update user: " + getUsername());
		return movieService.save(movie);
	}
	

	//@PreAuthorize("hasAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@GetMapping("/movies/find/{id}")
	public ResponseEntity<Movie> find(@PathVariable Long id) throws SQLException
	{
		System.out.println("In find user: " + getUsername());
		return movieService.find(id);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/movies/delete/{id}")
	public HttpStatus delete(@PathVariable Long id) throws SQLException
	{
		System.out.println("In delete user: " + getUsername());
		return movieService.delete(id);
	}	
	
	private String getUsername()
	{
	      return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}

/*

//@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})

*/

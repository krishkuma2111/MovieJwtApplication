package demo.springboot.service;


import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import demo.springboot.model.Movie;

@Service
public interface MovieService 
{
	public ResponseEntity<Movie>  save(Movie movie);
	public ResponseEntity<List<Movie>> findAll();
	public ResponseEntity<Movie> find(Long id) throws SQLException;
	public HttpStatus delete(Long id) throws SQLException ;
}

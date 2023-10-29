package demo.springboot.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import demo.springboot.model.Movie;
import demo.springboot.repository.MovieRepository;


@Service
public class MovieServiceImpl implements MovieService 
{
	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public ResponseEntity<Movie> save(Movie movie) 
	{
    	Movie savedMovie = movieRepository.save(movie);
    	HttpHeaders headers=new HttpHeaders();
//    	return new ResponseEntity<Movie>(savedMovie, getHeaders(), HttpStatus.OK);
    	return new ResponseEntity<Movie>(savedMovie, new HttpHeaders(), HttpStatus.OK);

	}

	@Override
	public ResponseEntity<List<Movie>> findAll() 
	{
		List<Movie> list=(List<Movie>)movieRepository.findAll();		
		return new ResponseEntity<List<Movie>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	@Override
	public ResponseEntity<Movie> find(Long id) throws SQLException 
	{
		Movie movie=movieRepository.findById(id).orElseThrow(()->new SQLException(String.format("Movie with id %d not found!", id)));
		return new ResponseEntity<Movie>(movie, new HttpHeaders(), HttpStatus.OK);
	}
	@Override
	public HttpStatus delete(Long id) throws SQLException  
	{
		movieRepository.findById(id).orElseThrow(()->new SQLException(String.format("Movie with id %d not found!", id)));
		movieRepository.deleteById(id);
		return HttpStatus.OK;
	}
	
	private HttpHeaders getHeaders()
	{
		final Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("charset", "utf-8");	
		HttpHeaders headers = new HttpHeaders();
		List<MediaType> mediaList=new ArrayList<>();
		mediaList.add(new MediaType("application","json"));
		headers.setAccept(mediaList);
		headers.setContentType(new MediaType("application","json", parameterMap));
		//headers.setBasicAuth()
		return headers;
	}
}

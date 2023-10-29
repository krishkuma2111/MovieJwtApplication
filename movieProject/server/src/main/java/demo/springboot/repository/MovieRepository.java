package demo.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.springboot.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> 
{

}

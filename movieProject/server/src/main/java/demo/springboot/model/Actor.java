package demo.springboot.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="ACTOR")

public class Actor  implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="ACTOR_SEQ")
	@SequenceGenerator(name="ACTOR_SEQ", sequenceName="ACTOR_SEQ", allocationSize=1)
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ACTOR_ID")
	private Long id;
	
	@Column(name="ACTOR_NAME")	
	private String name;
	
    @ManyToOne(targetEntity = Movie.class) 
    @JoinColumn(name="MOVIE_ID")
	private Movie movie;
    
	public Actor() {
		super();
	}
	public Actor(String name, Movie movie) {
		super();
		this.name = name;
		this.movie = movie;
	}
	public Actor(Long id, String name, Movie movie) {
		super();
		this.id = id;
		this.name = name;
		this.movie = movie;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    @JsonBackReference
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	@Override
	public String toString() {
		return String.format("Actor [id=%s, name=%s, movie=%s]", id, name, movie);
	}
	
}

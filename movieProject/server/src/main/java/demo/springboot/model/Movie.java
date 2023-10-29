package demo.springboot.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="MOVIE")
public class Movie  implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="MOVIE_SEQ")
	@SequenceGenerator(name="MOVIE_SEQ", sequenceName="MOVIE_SEQ", allocationSize=1)
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MOVIE_ID")
	private Long id;
	
	@Column(name="MOVIE_NAME")
	private String name;
	
    @OneToMany(targetEntity=Actor.class, cascade=CascadeType.ALL)
	private List<Actor> actors;
    
	public Movie() {
		super();
	}
	public Movie(String name, List<Actor> actors) {
		super();
		this.name = name;
		this.actors = actors;
	}
	public Movie(Long id, String name, List<Actor> actors) {
		super();
		this.id = id;
		this.name = name;
		this.actors = actors;
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
	
    @JsonManagedReference
	public List<Actor> getActors() {
		return actors;
	}
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	@Override
	public String toString() {
		return String.format("Movie [id=%s, name=%s, actors=%s]", id, name, actors);
	}
}	

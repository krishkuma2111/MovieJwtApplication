package demo.springboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Role 
{
    @Id 
	@GeneratedValue(generator="ROLE_SEQ")
	@SequenceGenerator(name="ROLE_SEQ", sequenceName="ROLE_SEQ", allocationSize=1)
    @Column(name="ROLE_ID")
    private Integer id;
     
    @Column(name="NAME")
    private String name;

    @ManyToOne(targetEntity = Login.class)
	@JoinColumn(name = "LOGIN_ID")	
    private Login login;
    
	public Role() {
		super();
	}

	public Role(Integer id) {
		super();
		this.id = id;
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}

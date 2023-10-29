package demo.springboot.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name="LOGIN")
public class Login implements UserDetails
{
	@Id
	@Column(name="LOGIN_ID")
	@GeneratedValue(generator="LOGIN_SEQ")
	@SequenceGenerator(name="LOGIN_SEQ", sequenceName="LOGIN_SEQ", allocationSize=1)	
	private Long id;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="PASSWORD")
	private String password;

	@OneToMany(targetEntity = Role.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Role> roles;
	
    public Login() {
		super();
	}

	public Login(String username, String password, List<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}


	public Login(Long id, String username, String password, List<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles)
        {
        	authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
 
    public List<Role> getRoles() {
        return roles;
    }
 
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
     
    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
	

package demo.springboot.model;

import java.util.List;

public class AuthResponse 
{
	private String username;
	private String accessToken;
	private List<String> roles;
	
	public AuthResponse() {
		super();
	}

	public AuthResponse(String username, String accessToken) {
		super();
		this.username = username;
		this.accessToken = accessToken;
	}

	public AuthResponse(String username, String accessToken, List<String> roles) {
		super();
		this.username = username;
		this.accessToken = accessToken;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}

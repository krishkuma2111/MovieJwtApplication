package demo.springboot.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demo.springboot.model.AuthRequest;
import demo.springboot.model.AuthResponse;
import demo.springboot.model.Login;
import demo.springboot.model.Role;
import demo.springboot.repository.LoginRepository;
import demo.springboot.utils.JwtTokenUtil;

@Service
public class LoginService 
{
	@Autowired
	LoginRepository repository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtTokenUtil jwtUtil;
	
	public AuthResponse login(AuthRequest request)
	{
		Optional<Login> o=repository.findByUsername(request.getUsername());
		Login login=o.get();
		String accessToken = jwtUtil.createToken(login);
        List<String> roles=login.getRoles().stream().map(item -> item.getName()).collect(Collectors.toList());;
		return new AuthResponse(request.getUsername(), accessToken, roles);
	}
	
	public AuthResponse register(AuthRequest request) throws SQLException
	{
		Optional<Login> o=repository.findByUsername(request.getUsername());
		
		if(o.isPresent())
		{
			throw new SQLException("User exists!");
		}
		
		List<Role> roles = getRoles(request);
	
		Login login = new Login(0L, request.getUsername(), encoder.encode(request.getPassword()), roles);
		
		String accessToken = jwtUtil.createToken(login);
		repository.save(login);
        
		List<String> stringRoles=getRoleToList(request);
		return new AuthResponse(request.getUsername(), accessToken, stringRoles);
	}
	
	public boolean isValid(String username, String password) throws SQLException {
		Optional<Login> o=repository.findByUsername(username);
		if(o.isPresent())
		{
			Login userDetails = o.get();
			return encoder.matches(password, userDetails.getPassword()) ? true : false;
		}
		return false;
	}
	
	public Login findByUsername(String username)
	{
		return repository.findByUsername(username).get();
	}
	
	private List<Role> getRoles(AuthRequest request)
	{
		List<Role> tmpRoles = new ArrayList<>();
		
		tmpRoles.add(new Role(request.getRole()));
		
		return tmpRoles;
	}
	
	private List<String> getRoleToList(AuthRequest request)
	{
		List<String> tmpRoles = new ArrayList<>();
		
		tmpRoles.add(request.getRole());
		
		return tmpRoles;
	}

}


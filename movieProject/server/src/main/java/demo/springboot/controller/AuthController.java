package demo.springboot.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import demo.springboot.model.AuthRequest;
import demo.springboot.model.AuthResponse;
import demo.springboot.service.LoginService;
import demo.springboot.utils.JwtTokenUtil;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/public")

public class AuthController {
	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	LoginService service;

	@Autowired
	PasswordEncoder encoder;

	public AuthController() {
		super();
		System.out.println("In authController");
	}

	/*
	 * 
	 * catch (MyResourceNotFoundException exc) {
         throw new ResponseStatusException(
           HttpStatus.NOT_FOUND, "Foo Not Found", exc);
    }
	 * 
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) 
			throws ResponseStatusException {		
		System.out.println("In login: "+request.getUsername());	 
		try
		{
			if(!service.isValid(request.getUsername(), request.getPassword()))
				throw new SQLException("Invalid username/password");
			AuthResponse response=service.login(request);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch(SQLException e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@GetMapping("/signout")
	public ResponseEntity<?> logout(@RequestHeader(name="Authorization") String token) throws SQLException {
		System.out.println("In logout: ");
		//SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid AuthRequest request) {
		System.out.println("In register: "+request.getUsername());
		try
		{
			AuthResponse response = service.register(request);
			return ResponseEntity.ok().body(response);
		}
		catch(SQLException e)
		{
System.out.println("register: "+e.getMessage());			
			throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage(), e);
		}
	}
}




/*

// @RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
 
//@CrossOrigin(maxAge = 3600, allowCredentials = "true")

//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Requestor-Type", "Authorization"}, exposedHeaders = "X-Get-Header")

	Authentication authentication = authManager.authenticate(
		new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
	Login user = (Login) authentication.getPrincipal();
	
*/

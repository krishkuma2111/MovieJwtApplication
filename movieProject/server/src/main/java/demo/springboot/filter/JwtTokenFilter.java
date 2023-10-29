package demo.springboot.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import demo.springboot.service.UserDetailsServiceImpl;
import demo.springboot.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer";

	@Autowired
	private JwtTokenUtil jwtUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		System.out.println("In JwtTokenFilter: ");
		
		  if (!hasAuthorizationBearer(request)) 
		  {
			  System.out.println("!hasAuthorizationBearer"); 
			  filterChain.doFilter(request, response); 
			  return; 
		  }
			  
		  String token = getAccessToken(request);
	
		  //System.out.println("Token: "+token);
	  
		  if (!jwtUtil.validateAccessToken(token)) 
		  {
			  System.out.println("!jwtUtil.validateAccessToken");
			  filterChain.doFilter(request, response); 
			  return; 
		  }
		  
		  setAuthenticationContext(token, request); 
		  
		  filterChain.doFilter(request, response); 
	}
		  
	  private boolean hasAuthorizationBearer(HttpServletRequest request) 
	  { 
		  String header = request.getHeader(AUTHORIZATION); 
		  System.out.println("In Filter, Header is: "+header);
		  if (ObjectUtils.isEmpty(header) || !header.startsWith(BEARER)) 
		  { 
			  return false; 
		  }
	  
		  return true; 
	  }
	  
	  private String getAccessToken(HttpServletRequest request) 
	  { 
		  String header = request.getHeader(AUTHORIZATION); 
		  String token = header.substring(7).trim();
		  //System.out.println("Token: "+token+" - Header: "+header); 
		  return token; 
	  }
	  
	  private void setAuthenticationContext(String token, HttpServletRequest request) 
	  { 
		  UserDetails userDetails = getUserDetails(token);
	  
		  if(userDetails==null)
		  {
			  return;			  
		  }
		  
		  UsernamePasswordAuthenticationToken authentication = new
		  UsernamePasswordAuthenticationToken(userDetails, null,
		  userDetails.getAuthorities());
		  
		  authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
		  
		  SecurityContextHolder.getContext().setAuthentication(authentication); 
	  }
		  
	  private UserDetails getUserDetails(String token) 
	  { 
		  
		  String username = jwtUtil.getUsername(token);
		  UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		  if(userDetails==null)
		  {
			  return null;
		  }
		  System.out.println("Login in filter() --- "+userDetails);
		  System.out.println("username --- "+username);
		  System.out.println("token --- "+token);
		  //System.out.println("Authenticated: "+SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

		  return userDetails; 
	  }
}


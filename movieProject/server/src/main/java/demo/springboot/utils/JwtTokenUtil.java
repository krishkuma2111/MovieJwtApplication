package demo.springboot.utils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import demo.springboot.model.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
 
@Component
public class JwtTokenUtil 
{
    @Value("${app.jwt.secretKey}")
    private String SECRET_KEY;
    
    @Value("${app.jwt.expirationTime}")
    private long EXPIRATION_TIME;
    
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer";
    
	public String getUsername(String token)
	{
		return parseClaims(token).getSubject();
	}

	public String extractToken(HttpServletRequest request)
	{
		String bearerToken=request.getHeader(AUTHORIZATION);
		if(bearerToken!=null && bearerToken.startsWith(BEARER))
		{
			return bearerToken.substring(7).trim();
		}
		return null;
	}

	public String createToken(Login login) {
		String username=login.getUsername();
		Claims claims=Jwts.claims().setSubject(username);
        List<String> roles=login.getRoles().stream().map(item -> item.getName()).collect(Collectors.toList());;
		//claims.put("roles", roles); 
        return Jwts.builder()
        		.claim("roles", roles)
                .setClaims(claims) 
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                .compact();                 
    }

    public boolean validateAccessToken(String token) 
    {
        try 
        {
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token);
            return true;
        } 
        catch(ExpiredJwtException e) 
        {
            System.err.println("JWT expired: "+e.getMessage());
        } 
        catch(IllegalArgumentException e) 
        {
            System.err.println("Token is null, empty or only whitespace: "+e.getMessage());
        } 
        catch(MalformedJwtException e) 
        {
            System.err.println("JWT is invalid: "+e);
        } 
        catch(UnsupportedJwtException e) 
        {
            System.err.println("JWT is not supported: "+e);
        } 
        catch(SignatureException e) 
        {
            System.err.println("Signature validation failed");
        }
         
        return false;
    }

    public String getSubject(String token) 
    {
        return parseClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
	public List<String> getRoles(String token) 
    {
        return (List<String>)parseClaims(token).get("roles");
    }

    public Claims parseClaims(String token) 
    {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
    
    public Boolean isTokenExpired(String token) 
    {
        return parseClaims(token).getExpiration().before(new Date());
    }

}

package demo.springboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import demo.springboot.model.Login;
import demo.springboot.repository.LoginRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{
	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Optional<Login> o=loginRepository.findByUsername(username);
		if(o.isPresent())
		{
			Login login=o.get();
	        List<String> roles=login.getRoles().stream().map(item -> item.getName()).collect(Collectors.toList());;
	        return new User(username, login.getPassword(), AuthorityUtils.createAuthorityList(roles));
		}
		return null;
	}
}

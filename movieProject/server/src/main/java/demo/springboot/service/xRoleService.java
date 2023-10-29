package demo.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.springboot.model.Role;
import demo.springboot.repository.RoleRepository;

//@Service
public class xRoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	public Role save(Role role)
	{
		return roleRepository.save(role);
	}
}


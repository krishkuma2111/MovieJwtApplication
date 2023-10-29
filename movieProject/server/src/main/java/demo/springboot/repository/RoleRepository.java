package demo.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.springboot.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> 
{

}

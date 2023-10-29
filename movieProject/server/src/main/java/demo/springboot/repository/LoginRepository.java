package demo.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.springboot.model.Login;

public interface LoginRepository extends JpaRepository<Login, Long> 
{
	public Optional<Login> findByUsername(String username);
}


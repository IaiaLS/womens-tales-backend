package com.example.womensTales.repository;

import java.util.List;
import java.util.Optional;

import com.example.womensTales.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
	
	public List <UsuarioEntity> findAllByUsuarioContainingIgnoreCase(String usuario);
	
	public Optional <UsuarioEntity> findByUsuario (String usuario);
}

package com.example.womensTales.repository;

import java.util.List;

import com.example.womensTales.entity.TemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TemaRepository extends JpaRepository<TemaEntity, Long>{
	
	List <TemaEntity> findAllByTituloContainingIgnoreCase(String titulo);
	
}

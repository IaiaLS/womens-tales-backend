package com.example.womensTales.repository;

import java.util.List;

import com.example.womensTales.entity.PostagemEntity;
import com.example.womensTales.entity.TemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PostagemRepository extends JpaRepository<PostagemEntity, Long>{
	
	public List <PostagemEntity> findAllByTituloContainingIgnoreCase(String titulo);
    public List <PostagemEntity> findAllByTema(TemaEntity tema);
	
}

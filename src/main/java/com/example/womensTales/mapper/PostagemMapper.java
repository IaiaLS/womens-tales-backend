package com.example.womensTales.mapper;

import com.example.womensTales.dto.PostagemDTO;
import com.example.womensTales.entity.PostagemEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostagemMapper {
    PostagemDTO toDTO(PostagemEntity entity);
    PostagemEntity fromDTO(PostagemDTO dto);
}

package com.example.womensTales.mapper;

import com.example.womensTales.dto.TemaDTO;
import com.example.womensTales.entity.TemaEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TemaMapper {
    TemaDTO toDTO(TemaEntity entity);
    TemaEntity fromDTO(TemaDTO dto);
}

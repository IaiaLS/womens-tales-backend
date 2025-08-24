package com.example.womensTales.mapper;

import com.example.womensTales.dto.*;
import com.example.womensTales.entity.UsuarioEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(UsuarioEntity entity);

    @Mappings({
            @Mapping(source = "nome", target = "nome"),
            @Mapping(source = "apelido", target = "apelido"),
            @Mapping(source = "profissao", target = "profissao"),
            @Mapping(source = "foto", target = "foto"),
            @Mapping(source = "usuario", target = "usuario"),
            @Mapping(source = "senha", target = "senha")
    })
    UsuarioEntity fromCreateDTO(UsuarioCreateDTO dto);
}

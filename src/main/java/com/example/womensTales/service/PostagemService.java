package com.example.womensTales.service;

import com.example.womensTales.dto.PostagemDTO;
import com.example.womensTales.entity.PostagemEntity;
import com.example.womensTales.entity.TemaEntity;
import com.example.womensTales.entity.UsuarioEntity;
import com.example.womensTales.mapper.PostagemMapper;
import com.example.womensTales.repository.PostagemRepository;
import com.example.womensTales.repository.TemaRepository;
import com.example.womensTales.repository.UsuarioRepository;
import com.example.womensTales.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostagemService {

    private final PostagemRepository postagemRepository;
    private final PostagemMapper postagemMapper;
    private final UsuarioRepository usuarioRepository;
    private final TemaRepository temaRepository;
    private final JwtService jwtService;


    public List<PostagemDTO> getAll() {
        return postagemRepository.findAll()
                .stream()
                .map(postagemMapper::toDTO)
                .toList();
    }

    public PostagemDTO getById(Long id) {
        return postagemRepository.findById(id)
                .map(postagemMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada"));
    }

    public List<PostagemDTO> getByTitulo(String titulo) {
        return postagemRepository.findAllByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(postagemMapper::toDTO)
                .toList();
    }

    public PostagemDTO create(PostagemDTO dto, String token) {
        String username = jwtService.extractUsername(token);

        UsuarioEntity usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        TemaEntity tema = temaRepository.findById(dto.getTemaId())
                .orElseThrow(() -> new RuntimeException("Tema não encontrado"));

        PostagemEntity entity = postagemMapper.fromDTO(dto);
        entity.setUsuario(usuario);
        entity.setTema(tema);
        entity.setData(LocalDate.now());

        PostagemEntity saved = postagemRepository.save(entity);
        return postagemMapper.toDTO(saved);
    }


    public PostagemDTO update(PostagemDTO dto, String token) {
        String username = jwtService.extractUsername(token);

        UsuarioEntity usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));

        PostagemEntity postagem = postagemRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada"));

        if (!postagem.getUsuario().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para atualizar esta postagem");
        }

        PostagemEntity entity = postagemMapper.fromDTO(dto);
        postagem.setData(LocalDate.now());
        postagem.setTitulo(entity.getTitulo());
        postagem.setTexto(entity.getTexto());

        PostagemEntity updated = postagemRepository.save(postagem);
        return postagemMapper.toDTO(updated);
    }

    public void delete(Long id, String token) {
        String username = jwtService.extractUsername(token);

        UsuarioEntity usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));

        PostagemEntity postagem = postagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada"));

        if (!postagem.getUsuario().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar esta postagem");
        }

        postagemRepository.delete(postagem);
    }


}


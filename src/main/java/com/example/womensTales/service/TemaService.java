package com.example.womensTales.service;

import com.example.womensTales.dto.TemaDTO;
import com.example.womensTales.entity.TemaEntity;
import com.example.womensTales.entity.UsuarioEntity;
import com.example.womensTales.mapper.TemaMapper;
import com.example.womensTales.repository.TemaRepository;
import com.example.womensTales.repository.UsuarioRepository;
import com.example.womensTales.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemaService {

    private final TemaRepository temaRepository;
    private final TemaMapper temaMapper;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;



    public List<TemaDTO> getAll() {
        return temaRepository.findAll()
                .stream()
                .map(temaMapper::toDTO)
                .toList();
    }

    public TemaDTO getById(Long id) {
        return temaRepository.findById(id)
                .map(temaMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado"));
    }

    public List<TemaDTO> getByTitulo(String titulo) {
        return temaRepository.findAllByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(temaMapper::toDTO)
                .toList();
    }

    public TemaDTO create(TemaDTO dto, String token) {
        String username = jwtService.extractUsername(token);

        UsuarioEntity usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        TemaEntity tema = temaMapper.fromDTO(dto);
        TemaEntity saved = temaRepository.save(tema);
        return temaMapper.toDTO(saved);
    }

    public TemaDTO update(TemaDTO dto, String token) {
        String username = jwtService.extractUsername(token);

        UsuarioEntity usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        TemaEntity tema = temaRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado"));

        tema.setTitulo(dto.getTitulo());
        tema.setStatus(dto.isStatus());
        tema.setPalavraChave(dto.getPalavraChave());

        TemaEntity updated = temaRepository.save(tema);
        return temaMapper.toDTO(updated);
    }

    public void delete(Long id, String token) {
        String username = jwtService.extractUsername(token);

        UsuarioEntity usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        TemaEntity tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado"));

        if (!tema.getPostagens().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível deletar um tema que possui postagens");
        }
        temaRepository.deleteById(id);
    }
}

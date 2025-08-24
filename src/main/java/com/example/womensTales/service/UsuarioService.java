package com.example.womensTales.service;

import java.util.List;
import java.util.Optional;

import com.example.womensTales.dto.UsuarioCreateDTO;
import com.example.womensTales.dto.UsuarioDTO;
import com.example.womensTales.entity.UsuarioEntity;
import com.example.womensTales.enums.RoleEnum;
import com.example.womensTales.mapper.UsuarioMapper;
import com.example.womensTales.security.JwtService;
import com.example.womensTales.security.UserDetailsImpl;
import com.example.womensTales.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;



    public Optional<UsuarioDTO> cadastrarUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioRepository.findByUsuario(usuarioCreateDTO.getUsuario()).isPresent())
            return Optional.empty();

        var entity = usuarioMapper.fromCreateDTO(usuarioCreateDTO);
        entity.setSenha(criptografarSenha(usuarioCreateDTO.getSenha()));
        entity.setRole(RoleEnum.USER);

        var salvo = usuarioRepository.save(entity);
        return Optional.of(usuarioMapper.toDTO(salvo));
    }


    public UsuarioDTO atualizarUsuario(UsuarioCreateDTO usuarioUpdateDTO, String token) {
        String usernameToken = jwtService.extractUsername(token);

        if (!usernameToken.equals(usuarioUpdateDTO.getUsuario())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode atualizar o seu próprio usuário");
        }

        UsuarioEntity existing = usuarioRepository.findByUsuario(usernameToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        existing.setNome(usuarioUpdateDTO.getNome());
        existing.setApelido(usuarioUpdateDTO.getApelido());
        existing.setProfissao(usuarioUpdateDTO.getProfissao());
        existing.setFoto(usuarioUpdateDTO.getFoto());

        if (usuarioUpdateDTO.getSenha() != null && !usuarioUpdateDTO.getSenha().isBlank()) {
            existing.setSenha(criptografarSenha(usuarioUpdateDTO.getSenha()));
        }

        UsuarioEntity salvo = usuarioRepository.save(existing);
        return usuarioMapper.toDTO(salvo);
    }


    public Optional<UserDetailsImpl> autenticarUsuario(String usuario, String senhaDigitada) {
        return usuarioRepository.findByUsuario(usuario)
                .filter(user -> passwordEncoder.matches(senhaDigitada, user.getSenha()))
                .map(UserDetailsImpl::new);
    }

    private String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

}

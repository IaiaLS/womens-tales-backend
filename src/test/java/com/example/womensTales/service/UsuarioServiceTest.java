package com.example.womensTales.service;

import com.example.womensTales.dto.UsuarioCreateDTO;
import com.example.womensTales.dto.UsuarioDTO;
import com.example.womensTales.entity.UsuarioEntity;
import com.example.womensTales.enums.RoleEnum;
import com.example.womensTales.mapper.UsuarioMapper;
import com.example.womensTales.repository.UsuarioRepository;
import com.example.womensTales.security.JwtService;
import com.example.womensTales.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioEntity usuario;
    private UsuarioDTO usuarioDTO;
    private UsuarioCreateDTO usuarioCreateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = UsuarioEntity.builder()
                .id(1L)
                .usuario("maria")
                .senha("hashed")
                .role(RoleEnum.USER)
                .build();

        usuarioDTO = UsuarioDTO.builder()
                .id(1L)
                .usuario("maria")
                .build();

        usuarioCreateDTO = UsuarioCreateDTO.builder()
                .usuario("maria")
                .senha("123456")
                .nome("Maria")
                .build();
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.empty());
        when(usuarioMapper.fromCreateDTO(usuarioCreateDTO)).thenReturn(usuario);
        when(passwordEncoder.encode("123456")).thenReturn("hashed");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        Optional<UsuarioDTO> result = usuarioService.cadastrarUsuario(usuarioCreateDTO);

        assertTrue(result.isPresent());
        assertEquals("maria", result.get().getUsuario());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void naoDeveCadastrarUsuarioSeJaExistir() {
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));

        Optional<UsuarioDTO> result = usuarioService.cadastrarUsuario(usuarioCreateDTO);

        assertTrue(result.isEmpty());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveAtualizarUsuarioSeForProprioUsuario() {
        UsuarioCreateDTO updateDTO = UsuarioCreateDTO.builder()
                .usuario("maria")
                .senha("novaSenha")
                .nome("Maria Atualizada")
                .build();

        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("novaSenha")).thenReturn("hashedNova");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.atualizarUsuario(updateDTO, "token");

        assertNotNull(result);
        assertEquals("maria", result.getUsuario());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void naoDeveAtualizarSeUsuarioTokenForDiferente() {
        UsuarioCreateDTO updateDTO = UsuarioCreateDTO.builder()
                .usuario("joao")
                .senha("teste")
                .build();

        when(jwtService.extractUsername("token")).thenReturn("maria");

        assertThrows(ResponseStatusException.class,
                () -> usuarioService.atualizarUsuario(updateDTO, "token"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveAutenticarUsuarioComSenhaCorreta() {
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("123456", "hashed")).thenReturn(true);

        Optional<UserDetailsImpl> result = usuarioService.autenticarUsuario("maria", "123456");

        assertTrue(result.isPresent());
    }

    @Test
    void naoDeveAutenticarUsuarioComSenhaIncorreta() {
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("errada", "hashed")).thenReturn(false);

        Optional<UserDetailsImpl> result = usuarioService.autenticarUsuario("maria", "errada");

        assertTrue(result.isEmpty());
    }

    @Test
    void deveListarTodosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        List<UsuarioDTO> result = usuarioService.getAllUsuarios();

        assertEquals(1, result.size());
        assertEquals("maria", result.get(0).getUsuario());
    }
}

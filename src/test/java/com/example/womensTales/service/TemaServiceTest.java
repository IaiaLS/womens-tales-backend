package com.example.womensTales.service;

import com.example.womensTales.dto.TemaDTO;
import com.example.womensTales.entity.TemaEntity;
import com.example.womensTales.entity.UsuarioEntity;
import com.example.womensTales.mapper.TemaMapper;
import com.example.womensTales.repository.TemaRepository;
import com.example.womensTales.repository.UsuarioRepository;
import com.example.womensTales.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TemaServiceTest {

    @Mock
    private TemaRepository temaRepository;

    @Mock
    private TemaMapper temaMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TemaService temaService;

    private UsuarioEntity usuario;
    private TemaEntity tema;
    private TemaDTO temaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = UsuarioEntity.builder().id(1L).usuario("maria").build();
        tema = TemaEntity.builder()
                .id(1L)
                .titulo("Direitos das Mulheres")
                .status(true)
                .palavraChave("igualdade")
                .postagens(new ArrayList<>())
                .build();

        temaDTO = TemaDTO.builder()
                .id(1L)
                .titulo("Direitos das Mulheres")
                .status(true)
                .palavraChave("igualdade")
                .build();
    }

    @Test
    void deveListarTodosOsTemas() {
        when(temaRepository.findAll()).thenReturn(List.of(tema));
        when(temaMapper.toDTO(tema)).thenReturn(temaDTO);

        List<TemaDTO> result = temaService.getAll();

        assertEquals(1, result.size());
        assertEquals("Direitos das Mulheres", result.get(0).getTitulo());
    }

    @Test
    void deveBuscarTemaPorId() {
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));
        when(temaMapper.toDTO(tema)).thenReturn(temaDTO);

        TemaDTO result = temaService.getById(1L);

        assertEquals("Direitos das Mulheres", result.getTitulo());
    }

    @Test
    void deveLancarExcecaoSeTemaNaoEncontradoPorId() {
        when(temaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> temaService.getById(1L));
    }

    @Test
    void deveCriarTemaComSucesso() {
        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(temaMapper.fromDTO(temaDTO)).thenReturn(tema);
        when(temaRepository.save(tema)).thenReturn(tema);
        when(temaMapper.toDTO(tema)).thenReturn(temaDTO);

        TemaDTO result = temaService.create(temaDTO, "token");

        assertNotNull(result);
        assertEquals("Direitos das Mulheres", result.getTitulo());
        verify(temaRepository, times(1)).save(tema);
    }

    @Test
    void deveAtualizarTemaComSucesso() {
        TemaDTO novoDTO = TemaDTO.builder()
                .id(1L)
                .titulo("Novo Título")
                .status(false)
                .palavraChave("mudança")
                .build();

        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));
        when(temaRepository.save(any(TemaEntity.class))).thenReturn(tema);
        when(temaMapper.toDTO(any(TemaEntity.class))).thenReturn(novoDTO);

        TemaDTO result = temaService.update(novoDTO, "token");

        assertEquals("Novo Título", result.getTitulo());
        verify(temaRepository, times(1)).save(any(TemaEntity.class));
    }

    @Test
    void deveDeletarTemaSeNaoTiverPostagens() {
        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));

        temaService.delete(1L, "token");

        verify(temaRepository, times(1)).deleteById(1L);
    }

    @Test
    void naoDeveDeletarTemaSeTiverPostagens() {
        tema.setPostagens(List.of(new com.example.womensTales.entity.PostagemEntity()));

        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));

        assertThrows(ResponseStatusException.class, () -> temaService.delete(1L, "token"));
        verify(temaRepository, never()).deleteById(any());
    }
}

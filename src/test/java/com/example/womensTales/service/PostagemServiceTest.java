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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostagemServiceTest {

    @Mock
    private PostagemRepository postagemRepository;

    @Mock
    private PostagemMapper postagemMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TemaRepository temaRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private PostagemService postagemService;

    private UsuarioEntity usuario;
    private TemaEntity tema;
    private PostagemEntity postagem;
    private PostagemDTO postagemDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = UsuarioEntity.builder().id(1L).usuario("maria").build();
        tema = TemaEntity.builder().id(1L).titulo("Direitos das Mulheres").build();
        postagem = PostagemEntity.builder()
                .id(1L)
                .titulo("Título Teste")
                .texto("Conteúdo Teste")
                .usuario(usuario)
                .tema(tema)
                .data(LocalDate.now())
                .build();

        postagemDTO = PostagemDTO.builder()
                .id(1L)
                .titulo("Título Teste")
                .texto("Conteúdo Teste")
                .temaId(1L)
                .build();
    }

    @Test
    void deveCriarPostagemComSucesso() {
        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));
        when(postagemMapper.fromDTO(postagemDTO)).thenReturn(postagem);
        when(postagemRepository.save(any(PostagemEntity.class))).thenReturn(postagem);
        when(postagemMapper.toDTO(postagem)).thenReturn(postagemDTO);

        PostagemDTO result = postagemService.create(postagemDTO, "token");

        assertNotNull(result);
        assertEquals("Título Teste", result.getTitulo());
        verify(postagemRepository, times(1)).save(any(PostagemEntity.class));
    }

    @Test
    void deveLancarExcecaoSeUsuarioNaoEncontradoAoCriar() {
        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> postagemService.create(postagemDTO, "token"));
    }

    @Test
    void deveAtualizarPostagemSeUsuarioForDono() {
        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(postagemRepository.findById(1L)).thenReturn(Optional.of(postagem));
        when(postagemMapper.fromDTO(postagemDTO)).thenReturn(postagem);
        when(postagemRepository.save(any(PostagemEntity.class))).thenReturn(postagem);
        when(postagemMapper.toDTO(postagem)).thenReturn(postagemDTO);

        PostagemDTO result = postagemService.update(postagemDTO, "token");

        assertEquals("Título Teste", result.getTitulo());
        verify(postagemRepository, times(1)).save(any(PostagemEntity.class));
    }

    @Test
    void deveLancarExcecaoSeOutroUsuarioTentarAtualizar() {
        UsuarioEntity outro = UsuarioEntity.builder().id(99L).usuario("joao").build();
        postagem.setUsuario(outro);

        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(postagemRepository.findById(1L)).thenReturn(Optional.of(postagem));

        assertThrows(ResponseStatusException.class,
                () -> postagemService.update(postagemDTO, "token"));
    }

    @Test
    void deveDeletarPostagemSeUsuarioForDono() {
        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(postagemRepository.findById(1L)).thenReturn(Optional.of(postagem));

        postagemService.delete(1L, "token");

        verify(postagemRepository, times(1)).delete(postagem);
    }

    @Test
    void deveLancarExcecaoAoDeletarSeUsuarioNaoForDono() {
        UsuarioEntity outro = UsuarioEntity.builder().id(99L).usuario("joao").build();
        postagem.setUsuario(outro);

        when(jwtService.extractUsername("token")).thenReturn("maria");
        when(usuarioRepository.findByUsuario("maria")).thenReturn(Optional.of(usuario));
        when(postagemRepository.findById(1L)).thenReturn(Optional.of(postagem));

        assertThrows(ResponseStatusException.class,
                () -> postagemService.delete(1L, "token"));
    }
}

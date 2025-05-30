package com.testetecnico.application.usecase.impl;

import com.testetecnico.application.usecase.interfaces.DevolverLivroUseCase;
import com.testetecnico.domain.entity.Aluno;
import com.testetecnico.domain.entity.Livro;
import com.testetecnico.domain.entity.Usuario;
import com.testetecnico.persistence.repository.BibliotecaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DevolverLivroUseCaseImplTest {

    @Mock
    private BibliotecaRepository bibliotecaRepository;

    @InjectMocks
    private DevolverLivroUseCaseImpl devolverLivroUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void devolverLivro_QuandoLivroEmprestadoParaUsuarioCorreto_DeveDevolverComSucesso() {
    
        int idLivro = 1;
        Livro livroMock = mock(Livro.class);
        Usuario usuarioMock = mock(Usuario.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.of(livroMock));
        when(livroMock.isDisponivel()).thenReturn(false);
        when(livroMock.getEmprestadoPara()).thenReturn(usuarioMock);
        when(usuarioMock.getNome()).thenReturn("Usuario Teste");

        
        boolean resultado = devolverLivroUseCase.devolverLivro(idLivro, usuarioMock);

        
        assertTrue(resultado);
        verify(livroMock).setDisponivel(true);
        verify(livroMock).setEmprestadoPara(null);
        verify(bibliotecaRepository).atualizarStatusLivro(livroMock);
    }

    @Test
    void devolverLivro_QuandoLivroEmprestadoParaAluno_DeveDevolverComSucessoEAdicionarCreditos() {
       
        int idLivro = 1;
        Livro livroMock = mock(Livro.class);
        Aluno alunoMock = mock(Aluno.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.of(livroMock));
        when(livroMock.isDisponivel()).thenReturn(false);
        when(livroMock.getEmprestadoPara()).thenReturn(alunoMock);
        when(alunoMock.getNome()).thenReturn("Aluno Teste");

    
        boolean resultado = devolverLivroUseCase.devolverLivro(idLivro, alunoMock);

   
        assertTrue(resultado);
        verify(livroMock).setDisponivel(true);
        verify(livroMock).setEmprestadoPara(null);
        verify(alunoMock).adicionarCreditos();
        verify(bibliotecaRepository).atualizarStatusLivro(livroMock);
    }

    @Test
    void devolverLivro_QuandoLivroNaoEncontrado_DeveRetornarFalse() {
      
        int idLivro = 1;
        Usuario usuarioMock = mock(Usuario.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.empty());

        
        boolean resultado = devolverLivroUseCase.devolverLivro(idLivro, usuarioMock);

        
        assertFalse(resultado);
        verify(bibliotecaRepository, never()).atualizarStatusLivro(any());
    }

    @Test
    void devolverLivro_QuandoLivroJaDisponivel_DeveRetornarFalse() {
        
        int idLivro = 1;
        Livro livroMock = mock(Livro.class);
        Usuario usuarioMock = mock(Usuario.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.of(livroMock));
        when(livroMock.isDisponivel()).thenReturn(true);

       
        boolean resultado = devolverLivroUseCase.devolverLivro(idLivro, usuarioMock);

        assertFalse(resultado);
        verify(livroMock, never()).setDisponivel(anyBoolean());
        verify(livroMock, never()).setEmprestadoPara(any());
        verify(bibliotecaRepository, never()).atualizarStatusLivro(any());
    }

    @Test
    void devolverLivro_QuandoLivroEmprestadoParaOutroUsuario_DeveRetornarFalse() {
        
        int idLivro = 1;
        Livro livroMock = mock(Livro.class);
        Usuario usuarioCorretoMock = mock(Usuario.class);
        Usuario usuarioIncorretoMock = mock(Usuario.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.of(livroMock));
        when(livroMock.isDisponivel()).thenReturn(false);
        when(livroMock.getEmprestadoPara()).thenReturn(usuarioCorretoMock);

        
        boolean resultado = devolverLivroUseCase.devolverLivro(idLivro, usuarioIncorretoMock);

      
        assertFalse(resultado);
        verify(livroMock, never()).setDisponivel(anyBoolean());
        verify(livroMock, never()).setEmprestadoPara(any());
        verify(bibliotecaRepository, never()).atualizarStatusLivro(any());
    }

} 
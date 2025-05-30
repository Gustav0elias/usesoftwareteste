package com.testetecnico.application.usecase.impl;

import com.testetecnico.application.usecase.interfaces.EmprestarLivroUseCase;
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

class EmprestarLivroUseCaseImplTest {

    @Mock
    private BibliotecaRepository bibliotecaRepository;

    @InjectMocks
    private EmprestarLivroUseCaseImpl emprestarLivroUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void emprestarLivro_QuandoLivroDisponivelEUsuarioComum_DeveEmprestarComSucesso() {
     
        int idLivro = 1;
        Livro livroMock = mock(Livro.class);
        Usuario usuarioMock = mock(Usuario.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.of(livroMock));
        when(livroMock.isDisponivel()).thenReturn(true);
        when(usuarioMock.getNome()).thenReturn("Usuario Teste");

      
        boolean resultado = emprestarLivroUseCase.emprestarLivro(idLivro, usuarioMock);

       
        assertTrue(resultado);
        verify(livroMock).setDisponivel(false);
        verify(livroMock).setEmprestadoPara(usuarioMock);
        verify(bibliotecaRepository).atualizarStatusLivro(livroMock);
    }

    @Test
    void emprestarLivro_QuandoLivroDisponivelEAlunoComCreditos_DeveEmprestarComSucessoERemoverCreditos() {
    
        int idLivro = 1;
        Livro livroMock = mock(Livro.class);
        Aluno alunoMock = mock(Aluno.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.of(livroMock));
        when(livroMock.isDisponivel()).thenReturn(true);
        when(alunoMock.podePegarEmprestado()).thenReturn(true);
        when(alunoMock.getNome()).thenReturn("Aluno Teste");

       
        boolean resultado = emprestarLivroUseCase.emprestarLivro(idLivro, alunoMock);

     
        assertTrue(resultado);
        verify(livroMock).setDisponivel(false);
        verify(livroMock).setEmprestadoPara(alunoMock);
        verify(alunoMock).removerCreditos();
        verify(bibliotecaRepository).atualizarStatusLivro(livroMock);
    }

    @Test
    void emprestarLivro_QuandoLivroNaoEncontrado_DeveRetornarFalse() {
       
        int idLivro = 1;
        Usuario usuarioMock = mock(Usuario.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.empty());

  
        boolean resultado = emprestarLivroUseCase.emprestarLivro(idLivro, usuarioMock);

  
        assertFalse(resultado);
        verify(bibliotecaRepository, never()).atualizarStatusLivro(any());
    }

    @Test
    void emprestarLivro_QuandoLivroIndisponivel_DeveRetornarFalse() {
 
        int idLivro = 1;
        Livro livroMock = mock(Livro.class);
        Usuario usuarioMock = mock(Usuario.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.of(livroMock));
        when(livroMock.isDisponivel()).thenReturn(false);

        
        boolean resultado = emprestarLivroUseCase.emprestarLivro(idLivro, usuarioMock);
 
        assertFalse(resultado);
        verify(livroMock, never()).setDisponivel(anyBoolean());
        verify(livroMock, never()).setEmprestadoPara(any());
        verify(bibliotecaRepository, never()).atualizarStatusLivro(any());
    }

    @Test
    void emprestarLivro_QuandoAlunoSemCreditos_DeveRetornarFalse() {
       
        int idLivro = 1;
        Livro livroMock = mock(Livro.class);
        Aluno alunoMock = mock(Aluno.class);

        when(bibliotecaRepository.buscarLivroPorId(idLivro)).thenReturn(Optional.of(livroMock));
        when(livroMock.isDisponivel()).thenReturn(true);
        when(alunoMock.podePegarEmprestado()).thenReturn(false);
        when(alunoMock.getNome()).thenReturn("Aluno Teste");

        
        boolean resultado = emprestarLivroUseCase.emprestarLivro(idLivro, alunoMock);

         
        assertFalse(resultado);
        verify(livroMock, never()).setDisponivel(anyBoolean());
        verify(livroMock, never()).setEmprestadoPara(any());
        verify(alunoMock, never()).removerCreditos();
        verify(bibliotecaRepository, never()).atualizarStatusLivro(any());
    }

} 
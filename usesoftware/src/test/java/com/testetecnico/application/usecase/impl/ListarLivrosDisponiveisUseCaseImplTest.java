package com.testetecnico.application.usecase.impl;

import com.testetecnico.domain.entity.Livro;
import com.testetecnico.persistence.repository.BibliotecaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListarLivrosDisponiveisUseCaseImplTest {

    @Mock
    private BibliotecaRepository bibliotecaRepository;

    @InjectMocks
    private ListarLivrosDisponiveisUseCaseImpl listarLivrosDisponiveisUseCase;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
   

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void listarLivrosDisponiveis_QuandoHouverLivros_DeveImprimirListaCorretamente() {
      
        Livro livro1 = new Livro(1, "Livro Teste 1", 10);
        Livro livro2 = new Livro(2, "Livro Teste 2", 15);
        List<Livro> livrosDisponiveis = Arrays.asList(livro1, livro2);

        when(bibliotecaRepository.listarLivrosDisponiveis()).thenReturn(livrosDisponiveis);

      
        listarLivrosDisponiveisUseCase.listarLivrosDisponiveis();

      
        verify(bibliotecaRepository).listarLivrosDisponiveis();
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("1 - Livro Teste 1"));
        assertTrue(consoleOutput.contains("2 - Livro Teste 2"));
    }
} 
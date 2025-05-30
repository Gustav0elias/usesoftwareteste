package com.testetecnico.application.usecase.impl;

import com.testetecnico.application.usecase.interfaces.ListarLivrosDisponiveisUseCase;
import com.testetecnico.persistence.repository.BibliotecaRepository;

public class ListarLivrosDisponiveisUseCaseImpl implements ListarLivrosDisponiveisUseCase {
    private final BibliotecaRepository bibliotecaRepository;

    public ListarLivrosDisponiveisUseCaseImpl(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    @Override
    public void listarLivrosDisponiveis() {
       bibliotecaRepository.listarLivrosDisponiveis().
       forEach(livro-> System.out.println(livro.getId() + " - " + livro.getTitulo()));
    }

}

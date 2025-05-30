package com.testetecnico.persistence.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.testetecnico.domain.entity.Livro;

 

 

public class BibliotecaRepository {

    private List<Livro> livros;

    public BibliotecaRepository() {
        this.livros = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) {
        this.livros.add(livro);
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livros.stream()
                .filter(Livro::isDisponivel)
                .toList();

    }
 

    public Optional<Livro> buscarLivroPorId(int id) {
        return livros.stream().
                filter(livro -> livro.getId() == id)
                .findFirst();
    }

    public boolean atualizarStatusLivro(Livro livro) {
        int index = livros.indexOf(livro);
        if (index != -1) {
            livros.set(index, livro);
            return true;
        }
        return false;
    }
}

package com.testetecnico.application.usecase.impl;

import java.util.Optional;

import com.testetecnico.application.usecase.interfaces.EmprestarLivroUseCase;
import com.testetecnico.domain.entity.Aluno;
import com.testetecnico.domain.entity.Livro;
import com.testetecnico.domain.entity.Usuario;
import com.testetecnico.persistence.repository.BibliotecaRepository;

 

public class EmprestarLivroUseCaseImpl implements EmprestarLivroUseCase {
    private final  BibliotecaRepository bibliotecaRepository;

    public EmprestarLivroUseCaseImpl(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    @Override
    public boolean emprestarLivro(int idLivro, Usuario usuario) {

        Optional<Livro> livroOptional = bibliotecaRepository.buscarLivroPorId(idLivro);
      
        if (livroOptional.isEmpty()) {
            System.out.println("Livro não encontrado.");
            return false;
        }
        

        Livro livro = livroOptional.get();

        if (!livro.isDisponivel()) {
            System.out.println("Livro não está disponível para empréstimo.");
            return false;
        }

        if (usuario instanceof Aluno){
            Aluno  aluno = (Aluno) usuario;
            if (!aluno.podePegarEmprestado()) {
                System.out.println("Aluno não tem créditos suficientes para pegar o livro.");
                return false;
            }
            aluno.removerCreditos();
        }
        
        livro.setDisponivel(false);
        livro.setEmprestadoPara(usuario);
        bibliotecaRepository.atualizarStatusLivro(livro);

        System.out.println("Livro " + livro.toString() + " emprestado com sucesso para " + usuario.getNome());
        return true;

       
    }

}

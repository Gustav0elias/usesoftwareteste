package com.testetecnico.application.usecase.impl;

 import java.util.Optional;

import com.testetecnico.application.usecase.interfaces.DevolverLivroUseCase;
import com.testetecnico.domain.entity.Aluno;
import com.testetecnico.domain.entity.Livro;
import com.testetecnico.domain.entity.Usuario;
import com.testetecnico.persistence.repository.BibliotecaRepository;

 

public class DevolverLivroUseCaseImpl implements DevolverLivroUseCase {
    private final BibliotecaRepository bibliotecaRepository;

    public DevolverLivroUseCaseImpl(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    @Override
    public boolean devolverLivro(int idLivro, Usuario usuario) {

       Optional<Livro> livroOptional = bibliotecaRepository.buscarLivroPorId(idLivro);
       
       if (livroOptional.isEmpty()) {
            System.out.println("Livro não encontrado.");
            return false;
       }

       Livro livro = livroOptional.get();
       
       if(livro.isDisponivel()){
            System.out.println("Livro não está emprestado.");
            return false;
       }

        if(!livro.getEmprestadoPara().equals(usuario) || livro.getEmprestadoPara() == null ){
            System.out.println("Livro não foi emprestado para este usuário.");
            return false;
       }

         if (usuario instanceof Aluno) {
            Aluno aluno = (Aluno) usuario;
            aluno.adicionarCreditos();
        }
                livro.setDisponivel(true);
        livro.setEmprestadoPara(null);
        bibliotecaRepository.atualizarStatusLivro(livro);

        System.out.println("Livro " + livro.toString() + " devolvido com sucesso por " + usuario.getNome());
        return true;
        
    }

}

package com.testetecnico;

import com.testetecnico.application.usecase.impl.DevolverLivroUseCaseImpl;
import com.testetecnico.application.usecase.impl.EmprestarLivroUseCaseImpl;
import com.testetecnico.application.usecase.impl.ListarLivrosDisponiveisUseCaseImpl;
import com.testetecnico.application.usecase.interfaces.DevolverLivroUseCase;
import com.testetecnico.application.usecase.interfaces.EmprestarLivroUseCase;
import com.testetecnico.application.usecase.interfaces.ListarLivrosDisponiveisUseCase;
import com.testetecnico.domain.entity.Aluno;
import com.testetecnico.domain.entity.Livro;
import com.testetecnico.domain.entity.Professor;
import com.testetecnico.persistence.repository.BibliotecaRepository;

public class Main {public static void main(String[] args) {
        BibliotecaRepository repository = new BibliotecaRepository();
        
        repository.adicionarLivro(new Livro(1, "Código Limpo", 1));
        repository.adicionarLivro(new Livro(2, "Entendendo Algoritmos", 2));
        repository.adicionarLivro(new Livro(3, "Padrões de Projeto", 1));
        
       
        Aluno aluno = new Aluno(1, "João Silva", 2);
        Professor professor = new Professor(2, "Maria Souza");
        
     
        EmprestarLivroUseCase emprestarLivroUseCaseImpl = new EmprestarLivroUseCaseImpl(repository);
        DevolverLivroUseCase devolverlivroUseCaseImpl = new DevolverLivroUseCaseImpl(repository);
        ListarLivrosDisponiveisUseCase listarLivrosDisponiveisUseCaseImpl = new ListarLivrosDisponiveisUseCaseImpl(repository);
     
        System.out.println("=== SISTEMA DE BIBLIOTECA ===");
        
      
        System.out.println("\nLivros disponíveis inicialmente:");
        listarLivrosDisponiveisUseCaseImpl.listarLivrosDisponiveis();
        
        //Representação de um aluno pegando um livro emprestado
        System.out.println("\nAluno tentando pegar livro emprestado...");
        emprestarLivroUseCaseImpl.emprestarLivro(1, aluno);
        System.out.println("Créditos do aluno " +aluno.getNome() + ": " + aluno.getCreditos());
        
        System.out.println("\nLivros disponíveis após empréstimos:");
        listarLivrosDisponiveisUseCaseImpl.listarLivrosDisponiveis();

        //Representação de um professor pegando um livro emprestado

        System.out.println("\nLivros disponíveis antes do empréstimo:");
        listarLivrosDisponiveisUseCaseImpl.listarLivrosDisponiveis();

        System.out.println("\nProfessor tentando pegar livro emprestado...");
        emprestarLivroUseCaseImpl.emprestarLivro(2, professor);
        
        System.out.println("\nLivros disponíveis após empréstimos:");
        listarLivrosDisponiveisUseCaseImpl.listarLivrosDisponiveis();
        
        // Representação de um aluno devolvendo um livro
        System.out.println("\nAluno devolvendo livro...");
        devolverlivroUseCaseImpl.devolverLivro(1, aluno);
        System.out.println("Créditos do aluno após devolução: " + aluno.getCreditos());
        
        System.out.println("\nLivros disponíveis após devolução:");
        listarLivrosDisponiveisUseCaseImpl.listarLivrosDisponiveis();
    }
}

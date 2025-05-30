package com.testetecnico.domain.entity;

public class Professor extends Usuario {

    public Professor(int id, String nome) {
        super(id, nome);
      
    }

    public boolean podePegarEmprestado(Livro livro){
        return true;
    }
 
}

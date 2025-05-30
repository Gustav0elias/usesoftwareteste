package com.testetecnico.domain.entity;

public class Aluno extends Usuario {
    private int creditos;

    public Aluno(int id, String nome, int creditos) {
        super(id, nome);
        this.creditos = creditos;
       
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    
    public void adicionarCreditos() {
        this.creditos ++;
    }

    public void removerCreditos(){
        if(this.creditos < 0){
          throw new IllegalArgumentException("Quantidade invalida!");
        }
            this.creditos --;
        
    }

    public boolean podePegarEmprestado() {
        return this.creditos > 0;
    }

}

package com.testetecnico.application.usecase.interfaces;

import com.testetecnico.domain.entity.Usuario;

public interface EmprestarLivroUseCase {
    
    public boolean emprestarLivro(int idLivro, Usuario idUsuario);
}

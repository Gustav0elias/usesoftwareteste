package com.testetecnico.application.usecase.interfaces;

import com.testetecnico.domain.entity.Usuario;

public interface DevolverLivroUseCase {
    public boolean devolverLivro(int idLivro, Usuario usuario);

}

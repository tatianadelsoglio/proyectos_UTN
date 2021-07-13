package com.integrador.integrador.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.integrador.integrador.modelo.Categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaRepositorio extends JpaRepository<Categoria,Integer> {
	Page<Categoria> findAll(Pageable pagina);
}

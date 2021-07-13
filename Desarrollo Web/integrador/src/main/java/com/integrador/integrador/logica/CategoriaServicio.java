package com.integrador.integrador.logica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.integrador.integrador.modelo.Categoria;
import com.integrador.integrador.persistencia.CategoriaRepositorio;

@Service
public class CategoriaServicio {
	@Autowired
	private CategoriaRepositorio repositorio;
	
	public Page<Categoria> listarTodasLasCategorias(Pageable pagina){
		return repositorio.findAll(pagina);
	}
	
}

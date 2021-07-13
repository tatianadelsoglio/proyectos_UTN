package com.integrador.integrador.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrador.integrador.logica.CategoriaServicio;
import com.integrador.integrador.modelo.Categoria;

@CrossOrigin(origins = "*") //Para poder acceder desde cualquier lado a mi backend
@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {
	
	@Autowired
	private CategoriaServicio servicio;
	
	@GetMapping
	public Page<Categoria> listarTodasLasCategorias(Pageable pagina){
		return servicio.listarTodasLasCategorias(pagina);
	}
}

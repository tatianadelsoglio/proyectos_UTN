package com.integrador.integrador.modelo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Turno {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String nombreApellido;
	private String fecha;
	private String hora;
	
	@ManyToOne
	private Categoria categoria;
}

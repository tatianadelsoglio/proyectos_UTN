package com.integrador.integrador.persistencia;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.integrador.integrador.modelo.Turno;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno,Integer> {
	
	Page<Turno> findById(Integer id,Pageable pagina);
	
	List<Turno> findByFechaAndHora(String fecha,String hora);
	
	Page<Turno> findByFecha(String fecha,Pageable pagina);
	
	Page<Turno> findByNombreApellidoContainingIgnoreCase(String nombreApellido,Pageable pagina);
	
	Page<Turno> findByNombreApellidoContainingIgnoreCaseAndFecha(String nombreApellido,String fecha,Pageable pagina);
}

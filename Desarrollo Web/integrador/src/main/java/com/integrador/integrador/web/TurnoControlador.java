package com.integrador.integrador.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.integrador.integrador.logica.TurnoServicio;
import com.integrador.integrador.modelo.Turno;

@CrossOrigin(origins = "*") //Para poder acceder desde cualquier lado a mi backend
@RestController
@RequestMapping("/turno")
public class TurnoControlador {
	
	@Autowired
	private TurnoServicio servicio;
	
	@PostMapping
	public ArrayList<String> guardarTurno(@RequestBody Turno t) {
		return servicio.registrarNuevoTurno(t);
	}
	
	@GetMapping(value = "/turnosdia")
	public Page<Turno> listarTurnosDelDia(Pageable pagina){
		return servicio.listarTurnosDelDia(pagina);
	}
	
	@GetMapping(value = "/horas")
	public ArrayList<String> listarHoras() {
		return servicio.getHoras();
	}
	
	@GetMapping(value = "/{id}")
	public Page<Turno> getTurno(@PathVariable(name="id") Integer id,Pageable pagina) {
		return servicio.getTurno(id,pagina);
	}
	
	@GetMapping(params = {"fecha"})
	public Page<Turno> listarTurnosPorFecha(String fecha,Pageable pagina){
		return servicio.listarTurnosPorFecha(fecha, pagina);
	}
	
	@GetMapping(params = {"nombreApellido","fecha"})
	public Page<Turno> listarTurnosPorNombreApellidoYFecha(String nombreApellido,String fecha,Pageable pagina){
		return servicio.listarTurnosPorNombreApellidoYFecha(nombreApellido, fecha, pagina);
	}
	
	@GetMapping(params = {"nombreApellido"})
	public Page<Turno> listarTurnosPorNombreApellido(String nombreApellido,Pageable pagina){
		return servicio.listarPorNombreApellido(nombreApellido, pagina);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public ArrayList<String> eliminar(@PathVariable(name = "id")Integer id) {
		return servicio.eliminar(id);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT) 
	public ArrayList<String> actualizar(@RequestBody Turno turno,@PathVariable(name = "id")Integer id) {
		return servicio.actualizar(turno,id);
	}
	
	
}

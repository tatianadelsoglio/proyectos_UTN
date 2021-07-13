package com.integrador.integrador.logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.integrador.integrador.modelo.Turno;
import com.integrador.integrador.persistencia.TurnoRepositorio;

@Service
public class TurnoServicio {
	@Autowired
	private TurnoRepositorio repositorio;
	
	public ArrayList<String> registrarNuevoTurno(Turno t) { //Para hacerlo sin retorno de String usar Data object trasnfer (DTO)
		ArrayList<String> respuesta = new ArrayList<String>();
		if (repositorio.findByFechaAndHora(t.getFecha(),t.getHora()).isEmpty()) {
			repositorio.save(t);
			respuesta.add("Turno Registrado Exitosamente para el día "+t.getFecha()+" a la hora "+t.getHora()+" hs");
			return respuesta;
		}else {	
			respuesta.add("No es posible registrar su turno para esa fecha y hora");
			return respuesta;
		}
	}
	
	public Page<Turno> getTurno(Integer id,Pageable pagina) {
		return repositorio.findById(id,pagina);
	}
	
	public ArrayList<String> getHoras(){
		ArrayList<String> horas = new ArrayList<String>();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime horaInicio = LocalTime.of(7,45);
		horaInicio.format(formato);
		LocalTime horaFin = LocalTime.of(20,00);
		horaFin.format(formato);
		boolean continuar = true;
		LocalTime ultima = horaInicio;
		while(continuar == true) {
			if(ultima.isBefore(horaFin)) {
				horas.add(ultima.plusMinutes(15).format(formato));
				ultima = ultima.plusMinutes(15);
			}else {
				continuar = false;
			}
		}
		
		return horas;
	}
	
	public Page<Turno> listarTurnosDelDia(Pageable pagina){
		LocalDate fechaDelDia = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		return repositorio.findByFecha(fechaDelDia.format(formato), pagina);
	}
	
	public Page<Turno> listarTurnosPorFecha(String fecha,Pageable pagina){
		return repositorio.findByFecha(fecha, pagina);
	}
	
	public Page<Turno> listarTurnosPorNombreApellidoYFecha(String nombreApellido,String fecha,Pageable pagina){
		return repositorio.findByNombreApellidoContainingIgnoreCaseAndFecha(nombreApellido, fecha, pagina);
	}
	
	public Page<Turno> listarPorNombreApellido(String nombreApellido,Pageable pagina){
		return repositorio.findByNombreApellidoContainingIgnoreCase(nombreApellido, pagina);
	}
	
	public ArrayList<String> eliminar(Integer id) {
		ArrayList<String> respuesta = new ArrayList<String>();
		System.out.println("Entro!!!!");
		Turno objetoAEliminar = repositorio.getOne(id);
		LocalDate fecha = LocalDate.parse(objetoAEliminar.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalTime hora = LocalTime.parse(objetoAEliminar.getHora(), DateTimeFormatter.ofPattern("kk:mm"));
		if (sePuedeEliminarOActualizar(fecha,hora) == true) {
			repositorio.deleteById(id);
			respuesta.add("El turno se ha eliminado exitosamente");
			return respuesta;
		}else {
			respuesta.add("ERROR!!!No es posible eliminar un turno ya transcurrido");
			return respuesta;
		}
	}
	
	public ArrayList<String> actualizar(Turno turno,Integer id) {
		ArrayList<String> respuesta = new ArrayList<String>();
		if(id == null) {
			throw new RuntimeException("Error...el turno no tiene id");
		}
		Turno objetoAActualizar = repositorio.getOne(id);
		turno.setId(id);
		LocalDate fecha = LocalDate.parse(objetoAActualizar.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalTime hora = LocalTime.parse(objetoAActualizar.getHora(), DateTimeFormatter.ofPattern("kk:mm"));
		if(sePuedeEliminarOActualizar(fecha,hora) == true) {
			repositorio.save(turno);
			respuesta.add("El turno se ha actualizado exitosamente");
			return respuesta;
		}else {
			respuesta.add("ERROR!!!No es posible actualizar un turno ya transcurrido");
			return respuesta;
		}
	}
	
	public boolean sePuedeEliminarOActualizar(LocalDate fecha,LocalTime hora) {
		LocalDate fechaActual = LocalDate.now();
		LocalTime horaActual  = LocalTime.now();
		if (fechaActual.isBefore(fecha)) {
			return true;
		}else {
			if(fechaActual.isAfter(fecha)) {
				return false;
			}else {
				if(fechaActual.isEqual(fecha)) {
					if(horaActual.isBefore(hora)) {
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			}
		}
	}
}

import { Component, OnInit } from '@angular/core';
import { TurnoService, Turno } from 'src/app/servicios/turno.service';

@Component({
  selector: 'app-turnos-del-dia',
  templateUrl: './turnos-del-dia.component.html',
  styleUrls: ['./turnos-del-dia.component.css']
})
export class TurnosDelDiaComponent implements OnInit {
  turnos : Turno[] = [];
  fechaDelDia : string;
  txtPersona : string;
  busqueda_no_valida : string;
  constructor(private servicioTurno : TurnoService) { }

  ngOnInit(): void {
    this.servicioTurno.getTurnosDelDia().subscribe(respuesta => {
      this.turnos = respuesta.content;
      this.fechaDelDia = respuesta.content[0].fecha;
    });
  }

  eliminarTurno(id : number){
    this.servicioTurno.eliminarTurno(id);
  }

  buscar(){
    if(this.esCorrecto(this.txtPersona) == false){
      this.busqueda_no_valida = "Ingrese un nombre de persona valido";
    }else{
      this.servicioTurno.getTurnosXNombreYFecha(this.txtPersona,this.fechaDelDia).subscribe(respuesta => {
        this.turnos = respuesta.content;
        this.busqueda_no_valida = undefined;
      });
    }
  }

  limpiarFiltro(){
    this.ngOnInit();
    this.txtPersona = undefined;
    this.busqueda_no_valida = undefined;
  }

  esCorrecto(valor : string) : boolean {
    return valor.trim().length != 0;
  }
}

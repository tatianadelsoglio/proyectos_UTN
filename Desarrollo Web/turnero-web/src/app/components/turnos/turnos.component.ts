import { Component, OnInit } from '@angular/core';
import { Turno, TurnoService } from 'src/app/servicios/turno.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-turnos',
  templateUrl: './turnos.component.html',
  styleUrls: ['./turnos.component.css']
})
export class TurnosComponent implements OnInit {
  txtPersona : string;
  txtFecha : string;
  idTurno : string;
  turnos : Turno[] = [];
  constructor(private servicioTurno : TurnoService,private _formatFecha : DatePipe,private router : Router) { }

  ngOnInit(): void {
  }

  buscarXid() {
    this.servicioTurno.getTurnosXid(this.idTurno).subscribe(respuesta => {
      this.turnos = respuesta.content;
      this.idTurno = undefined;
    });
  }

  buscarXidParamID(id : any){
    this.servicioTurno.getTurnosXid(id).subscribe(respuesta => {
      this.turnos = respuesta.content;
      this.idTurno = undefined;
    });
  }

  buscarXPersona() {
    this.servicioTurno.getTurnosXNombre(this.txtPersona).subscribe(respuesta => {
      this.turnos = respuesta.content;
      this.txtPersona = undefined;
    });
  }

  buscarXfecha(){
    this.servicioTurno.getTurnosXFecha(this._formatFecha.transform(this.txtFecha,'dd/MM/yyyy')).subscribe(respuesta =>{
      this.turnos = respuesta.content;
      this.txtFecha = undefined;
    });
  }

  eliminarTurno(id : number){
    this.servicioTurno.eliminarTurno(id).subscribe(respuesta => {
      alert(respuesta);
      this.buscarXidParamID(id);
    })
  }

  actualizarTurno(id : number){
    this.router.navigate(['/actualizar-turno',id]);
  }

}

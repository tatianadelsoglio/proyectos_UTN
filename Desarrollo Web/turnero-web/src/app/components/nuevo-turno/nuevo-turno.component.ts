import { Component, OnInit } from '@angular/core';
import { Categoria, TurnoService } from 'src/app/servicios/turno.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-nuevo-turno',
  templateUrl: './nuevo-turno.component.html',
  styleUrls: ['./nuevo-turno.component.css']
})
export class NuevoTurnoComponent implements OnInit {
  categorias : Categoria[] = [];
  horas : any[] = [];
  respuestaAPI : any;
  
  constructor(private servicioTurno : TurnoService,private _formatFecha : DatePipe) { }

  ngOnInit(): void {
    this.servicioTurno.getCategorias().subscribe(respuesta => {
      this.categorias = respuesta.content;
    });
    this.servicioTurno.getHoras().subscribe(respuesta => {
      this.horas = respuesta;
    })
  }

  guardar(formulario){
    formulario.value.fecha = this._formatFecha.transform(formulario.value.fecha,'dd/MM/yyyy');
    this.servicioTurno.guardarTurno(formulario).subscribe(respuesta => {
      this.respuestaAPI = respuesta;
    });
  }

}

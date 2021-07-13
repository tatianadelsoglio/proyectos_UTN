import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TurnoService, Categoria } from 'src/app/servicios/turno.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-actualizar-turno',
  templateUrl: './actualizar-turno.component.html',
  styleUrls: ['./actualizar-turno.component.css']
})
export class ActualizarTurnoComponent implements OnInit {
  turno : any = {}
  categorias : Categoria[] = [];
  horas : any[] = [];
  respuestaAPI : any;
  idAActualizar : any;
  
  constructor(private activate : ActivatedRoute,private servicioTurno : TurnoService,private _formatFecha : DatePipe) { 
    this.activate.params.subscribe((parametros) => {
      parametros['id'];
      this.idAActualizar = parametros['id'];
      this.servicioTurno.getTurnosXid(parametros['id']).subscribe(respuesta => {
        this.turno = respuesta.content[0];
      });
    })
  }

  ngOnInit(): void {
    this.servicioTurno.getCategorias().subscribe(respuesta => {
      this.categorias = respuesta.content;
    });
    this.servicioTurno.getHoras().subscribe(respuesta => {
      this.horas = respuesta;
    })
  }

  actualizar(formulario){
    formulario.value.fecha = this._formatFecha.transform(formulario.value.fecha,'dd/MM/yyyy');
    this.servicioTurno.actualizarTurno(formulario,this.idAActualizar).subscribe(respuesta => {
      this.respuestaAPI = respuesta;
    })
  }

}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NgForm } from '@angular/forms';
import { identifierModuleUrl } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class TurnoService {

  private URL_API_TURNOSDIA : string = "http://localhost:8080/turno/turnosdia";
  private URL_API : string = "http://localhost:8080/turno";
  private URL_API_FILTRO : string = "http://localhost:8080/turno?";
  private URL_API_CATEGORIA : string = "http://localhost:8080/categoria";
  private URL_API_HORAS : string = "http://localhost:8080/turno/horas";
  constructor(private _http : HttpClient) { 

  }

  getTurnosDelDia() : Observable<any>{
    return this._http.get<Turno[]>(this.URL_API_TURNOSDIA);
  }

  getCategorias() : Observable<any>{
    return this._http.get<Categoria[]>(this.URL_API_CATEGORIA);
  }

  getHoras() : Observable<any>{
    return this._http.get<any[]>(this.URL_API_HORAS);
  }

  eliminarTurno(id : number) {
    return this._http.delete(this.URL_API+"/"+id);
  }

  actualizarTurno(formulario,id : number){
    return this._http.put(this.URL_API+"/"+id,formulario.value);
  }

  getTurnosXNombreYFecha(nombreApellido : string,fecha : string) : Observable<any>{
    return this._http.get<Turno[]>(this.URL_API_FILTRO+"nombreApellido="+nombreApellido+"&fecha="+fecha);
  }

  getTurnosXid(id : string) : Observable<any>{
    return this._http.get<Turno[]>(this.URL_API+"/"+id);
  }

  getTurnosXNombre(nombreApellido : string) : Observable<any>{
    return this._http.get<Turno[]>(this.URL_API+"?nombreApellido="+nombreApellido);
  }

  getTurnosXFecha(fecha : string) : Observable<any>{
    return this._http.get<Turno[]>(this.URL_API+"?fecha="+fecha)
  }

  guardarTurno(formulario) : Observable<any>{
    return this._http.post<Turno>(this.URL_API,formulario.value);
  }
}

export interface Turno{
  id : number;
  nombreApellido : string;
  fecha : string;
  hora : string;
  categoria : Categoria;
}

export interface Categoria{
  id : number;
  descripcion : string;
  nombre : string;
}

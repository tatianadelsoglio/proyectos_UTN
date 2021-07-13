import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { TurnosDelDiaComponent } from './components/turnos-del-dia/turnos-del-dia.component';
import { HomeComponent } from './components/home/home.component';
import { TurnosComponent } from './components/turnos/turnos.component';
import { NuevoTurnoComponent } from './components/nuevo-turno/nuevo-turno.component';
import { ActualizarTurnoComponent } from './components/actualizar-turno/actualizar-turno.component';


const routes: Routes = [
  {path : 'login',component : LoginComponent},
  {path : 'turnos_del_dia',component : TurnosDelDiaComponent},
  {path : 'home',component : HomeComponent},
  {path : 'turnos',component : TurnosComponent},
  {path : 'nuevo-turno',component : NuevoTurnoComponent},
  {path : 'actualizar-turno/:id', component : ActualizarTurnoComponent},
  {path : '**', pathMatch:'full', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

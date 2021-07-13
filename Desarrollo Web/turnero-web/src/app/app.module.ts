import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { TurnosDelDiaComponent } from './components/turnos-del-dia/turnos-del-dia.component';
import { TurnoService } from './servicios/turno.service';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import { TurnosComponent } from './components/turnos/turnos.component';
import { DatePipe } from '@angular/common';
import { NuevoTurnoComponent } from './components/nuevo-turno/nuevo-turno.component';
import { ActualizarTurnoComponent } from './components/actualizar-turno/actualizar-turno.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    HomeComponent,
    TurnosDelDiaComponent,
    TurnosComponent,
    NuevoTurnoComponent,
    ActualizarTurnoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    TurnoService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

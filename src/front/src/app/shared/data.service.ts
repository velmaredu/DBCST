import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs'

@Injectable({ providedIn: 'root' })
export class DataService {

  // Usamos mensajes para mostrar el resultado de la operacion
  private mensaje = new BehaviorSubject('Lista de Vinos'); // hay que inicializarlo
  mensajeActual = this.mensaje.asObservable(); // Lo exponemos como un observable

  // Usamos esta variable para indicar si hay que mostrar o no el mensaje
  private mostrarMensaje = new BehaviorSubject<boolean>(false);
  mostrarMensajeActual = this.mostrarMensaje.asObservable();
  constructor() { }

  // Para actualizar mensaje
  cambiarMensaje(mensaje: string) {
    this.mensaje.next(mensaje);
  }

  cambiarMostrarMensaje(valor: boolean) {
    this.mostrarMensaje.next(valor);
  }
}
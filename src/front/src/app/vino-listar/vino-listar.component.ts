import { Component, OnInit } from '@angular/core';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { Vino } from '../shared/app.model';
import { DataService } from '../shared/data.service';

@Component({
  selector: 'app-vino-listar',
  templateUrl: './vino-listar.component.html',
  styleUrls: ['./vino-listar.component.css']
})
export class VinoListarComponent implements OnInit {
  Vinos!: Vino[];
  mostrarMensaje!: boolean;
  mensaje!: string;

  // Inyectamos los servicios
  constructor(private clienteApiRest: ClienteApiRestService, private datos: DataService) { }

  //método ejecutado tras la construcción del componente. Es el lugar adecuado para cargar datos
  ngOnInit() {
    //console.log("Dentro funcion ngOnInit de Listar");

    // capturamos valor de mostrarMensaje. Recordar que la variable es un Observable
    this.datos.mostrarMensajeActual.subscribe(
      valor => this.mostrarMensaje = valor
    )
    //console.log("Valor actual de si mostrar mensaje: " + this.mostrarMensaje);

    // capturamos el valor de mensaje
    this.datos.mensajeActual.subscribe(
      valor => this.mensaje = valor
    )
    //console.log("Valor actual del mensaje: " + this.mensaje);

    // // Llamamos al metodo que implementa la llamada GET, retornando el cuerpo de la respuesta
    // this.clienteApiRest.getAllVinos().subscribe(
    //   (resp: Vino[]) => {
    //     //console.log("datos: " + resp);
    //     resp.forEach(function (data) {
    //       //console.log("Nombre Comercial: " + data.nombre_comercial);
    //     });
    //   },
    //   err => console.log("Error: " + err)
    // );

    /*** Cargamos vinos accediendo a la respuesta */
    this.getVinos_AccesoResponse();
  }

  getVinos_AccesoResponse() {
    this.clienteApiRest.getAllVinos_ConResponse().subscribe(
      resp => {
        //console.log("Cabeceras: " + resp.headers.keys());
        //console.log("Status: " + resp.status);
        if (resp.status < 400) { // Si no hay error en la respuesta
          this.Vinos = resp.body!; // se accede al cuerpo de la respuesta
        } else {
          this.mensaje = 'Error al acceder a los datos';
          this.mostrarMensaje = true;
        }
      },
      err => {
        console.log("Error al traer la lista: " + err.message);
        throw err;
      }
    )
  }
  
  borrar(id: Number) {
    this.clienteApiRest.borrarVino(String(id)).subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          // actualizamos variable compartida
          this.mostrarMensaje = true;
          // actualizamos variable compartida
          this.mensaje = resp.body; // mostramos el mensaje retornado por el API
          //Actualizamos la lista de vinos en la vista
          this.getVinos_AccesoResponse();
        } else {
          this.mostrarMensaje = true;
          this.mensaje = "Error al eliminar registro";
        }
      },
      err => {
        console.log("Error al borrar: " + err.message);
        throw err;
      }
    )
  }
}
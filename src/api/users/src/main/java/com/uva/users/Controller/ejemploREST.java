package com.uva.users.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uva.users.Exception.VinoConRelacionException;
import com.uva.users.Exception.VinoException;
import com.uva.users.Model.Bodega;
import com.uva.users.Model.Vino;
import com.uva.users.Model.VinoConRelacion;
import com.uva.users.Repository.BodegaRepository;
import com.uva.users.Repository.VinoConRelacionRepository;
import com.uva.users.Repository.VinoRepository;

@RestController
@RequestMapping("/TiendaVinos")
@CrossOrigin(origins = "*")
class ejemploREST {
    private final VinoRepository repository;
    private final VinoConRelacionRepository repository2;
    private final BodegaRepository repository3;

    ejemploREST(VinoRepository repository, VinoConRelacionRepository repository2, BodegaRepository repository3) {
        this.repository = repository;
        this.repository2 = repository2;
        this.repository3 = repository3;
    }

    // GETTERS

    @GetMapping("/cartaBodegas/{id}")
    public Bodega getBodega(@PathVariable int id) {
        Bodega bodega = repository3.findById(id).orElseThrow(() -> new VinoException("Sin resultado"));
        return bodega;
    }

    @GetMapping("/cartaVinosConRelacion/{id}")
    public VinoConRelacion getVinoConRelacion(@PathVariable int id) {
        VinoConRelacion vinoconrelacion = repository2.findById(id)
                .orElseThrow(() -> new VinoConRelacionException("Sin resultado"));
        return vinoconrelacion;
    }

    @GetMapping()
    public List<Vino> getVinos() {
        return repository.findAll();
    }

    @GetMapping(value = "/getHTML", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getHTML() {
        return "<html>\n" + "<header><title>Bienvenido</title></header>\n" +
                "<body>\n" + "<p style=\"color:red\">" + "Hola Mundo" + "</p>\n" + "</body>\n" + "</html>";
    }

    @GetMapping("/cartaVinos/{id}")
    public Vino getCarta(@PathVariable int id) {
        Vino vino = repository.findById(id).orElseThrow(() -> new VinoException("Sin resultado"));
        return vino;
    }

    @GetMapping(value = { "/nombreVino", "/nombreVino/{nombre}" })
    public String getNombre(@PathVariable(required = false) String nombre) {
        if (nombre != null) {
            return "Vino: " + nombre;
        } else {
            return "Vino vacío";
        }
    }

    @GetMapping("/cartaVinos")
    public String getCartaConQuery(@RequestParam List<String> nombres) {
        return "Nombre del vino: " + nombres;
    }

    // 1.4.1
    @GetMapping(value = { "/informacion/{id}" })
    public String getCaracteristicasVinos(@PathVariable int id,
            @RequestParam(value = "campos", defaultValue = "precio", required = false) List<String> specs) {
        if (id == 87) {
            StringBuilder bld = new StringBuilder();
            for (int i = 0; i < specs.size(); i++) {
                if (specs.get(i).equals("precio")) {
                    bld.append("Precio: " + 30 + "€" + "\n");
                } else if (specs.get(i).equals("denominacion")) {
                    bld.append("Denominación: " + "Ribera de Duero" + "\n");
                } else if (specs.get(i).equals("bodega")) {
                    bld.append("Bodega: " + "Toro" + "\n");
                }
            }
            return bld.toString();
        } else {
            return "ID no encontrado";
        }
    }

    @GetMapping("/responsePersonalizado/{id}")
    public ResponseEntity<String> getVino(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("cabecera-personalizada", "valorPersonalizado");
        if (id <= 10) {
            return new ResponseEntity<>("menor_igual a 10", headers,
                    HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("mayor que 10", headers, HttpStatus.OK);
        }
    }

    // A partir de aquí JPA P1.4

    @GetMapping("/VinoPorNombre/{nombre}")
    public Vino getVinoPorNombre_Comercial(@PathVariable String nombre) {
        return repository.findByNombreComercial(nombre)
                .orElseThrow(() -> new VinoException("no se ha encontrado el vino de nombre " + nombre));
    }

    @GetMapping("/VinoPorPrecio")
    public List<Vino> getVinoPorPrecio(@RequestParam Float precio1, @RequestParam Float precio2) {
        return repository.findByPrecioBetween(precio1, precio2);
    }

    // POSTS

    @PostMapping(value = "nuevaBodega", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newBodega(@RequestBody Bodega newBodega) {
        try {
            repository3.save(newBodega);
            return "Nuevo registro creado";
        } catch (Exception e) {
            throw new VinoException("Error al crear el nuevo registro.");
        }
    }

    @PostMapping(value = "nuevoVinoConRelacion", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newVinoConRelacion(@RequestBody VinoConRelacion newVinoConRelacion) {
        try {
            repository2.save(newVinoConRelacion);
            return "Nuevo registro creado";
        } catch (Exception e) {
            throw new VinoException("Error al crear el nuevo registro.");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newVino(@RequestBody Vino newVino) {
        try {
            repository.save(newVino);
            return "Nuevo registro creado";
        } catch (Exception e) {
            throw new VinoException("Error al crear el nuevo registro.");
        }
    }

    @PostMapping("/id")
    public String postVinos(@RequestBody String body) {
        return "Contenido cuerpo petición post: " + body;
    }

    // PUTS

    @PutMapping(value = "/cartaVinos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateVino(@PathVariable int id, @RequestBody Vino newVino) {
        try {
            newVino.setId(id);
            repository.save(newVino);
            return "Registro actualizado";
        } catch (Exception e) {
            throw new VinoException("Error al actualizar el registro.");
        }
    }

    @PutMapping("/id")
    public String putVinos(@RequestBody String body, @PathVariable Long id) {
        return "Realizada operación Put. Con Id: " + id + ". Contenido cuerpo petición put: " + body;
    }

    // PATCHS

    @PatchMapping(path = "/{id}")
    public String patchPrice(@PathVariable int id, @RequestBody String body) {
        try {
            float precio = Float.parseFloat(body);
            Vino vino = repository.findById(id).orElseThrow(() -> new VinoException("Sin resultado"));
            vino.setPrecio(precio);
            repository.save(vino);
            return "Precio actualizado";
        } catch (Exception e) {
            return "El precio no se ha podido actualizar";
        }

    }

    // DELETES

    @DeleteMapping("/cartaVinos/{id}")
    public String deleteVino(@PathVariable int id) {
        try {
            repository.deleteById(id);
            return "Registro eliminado";
        } catch (Exception e) {
            throw new VinoException("Error al eliminar registro.");
        }
    }

    /*
     * EJ 1
     * 
     * de P1.4**
     * 
     * @param json
     * 
     * @return
     */
    @DeleteMapping("/BorrarPorDenominacion_Categoria")
    public List<Vino> deletePorDenominacion_Categoria(@RequestBody String json) {
        List<Vino> borrados = new ArrayList<Vino>();
        try {
            JSONObject jsonObjeto = new JSONObject(json);
            String denominacion = jsonObjeto.getString("denominacion");
            String categoria = jsonObjeto.getString("categoria");
            boolean existe = repository.existsVinoByDenominacionAndCategoria(denominacion, categoria);
            if (existe) {
                borrados = repository.deleteByDenominacionAndCategoria(denominacion, categoria);
                return borrados;
            } else {
                System.err.println("No existen vinos de la categoría y denominación");
                return borrados;
            }
        } catch (Exception e) {
            System.err.println(e);
            return borrados;
        }
    }

    /*
     * EJ 2
     * 
     * de P1.4**
     * 
     * @param json
     * 
     * @return
     */
    @GetMapping("/ContarPorDenominacion")
    public int getCountPorDenominacion(@RequestBody String json) {
        try {
            JSONObject jsonObjeto = new JSONObject(json);
            String denominacion = jsonObjeto.getString("denominacion");
            return repository.countByDenominacion(denominacion);
        } catch (Exception e) {
            System.err.println(e);
            return -1;
        }
    }

    /**
     * EJ 3 de P1.4
     * 
     * @param json
     * @return
     */
    @GetMapping("/ContarPorNoDenominacion")
    public int getCountPorNoDenominacion(@RequestBody String json) {
        try {
            JSONObject jsonObjeto = new JSONObject(json);
            String denominacion = jsonObjeto.getString("denominacion");
            return repository.countByDenominacionNot(denominacion);
        } catch (Exception e) {
            System.err.println(e);
            return -1;
        }
    }

}

package com.uva.users.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Vino")
public class Vino {
    @Id
    @GeneratedValue
    private Integer Id;
    @Size(max = 50) // si no se pone esta anotación lo creo por defecto con size=255
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    @Size(max = 30)
    private String denominacion;
    @Size(max = 30)
    private String categoria;
    @Column(nullable = false)
    private Float precio;
    private Integer bodega_id;

    Vino() {
    }

    Vino(String nombreComercial, String denominacion, String categoria, Float precio, Integer bodega) {
        this.nombreComercial = nombreComercial;
        this.denominacion = denominacion;
        this.categoria = categoria;
        this.precio = precio;
        this.bodega_id = bodega;
    }

    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getNombre_comercial() {
        return nombreComercial;
    }

    public void setNombre_comercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getBodega_id() {
        return bodega_id;
    }

    public void setBodega_id(Integer bodega_id) {
        this.bodega_id = bodega_id;
    }
}

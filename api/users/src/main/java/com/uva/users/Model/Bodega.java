package com.uva.users.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;

@Entity
@Table(name = "bodega")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")

    private String nombre;
    @Basic(optional = false)
    @Column(name = "CIF")
    private String cif;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @OneToMany(mappedBy = "bodegaId", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<VinoConRelacion> vinoCollection;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCif() {
        return cif;
    }
    public void setCif(String cif) {
        this.cif = cif;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public List<VinoConRelacion> getVinoCollection() {
        return vinoCollection;
    }
    public void setVinoCollection(List<VinoConRelacion> vinoCollection) {
        this.vinoCollection = vinoCollection;
    }

}

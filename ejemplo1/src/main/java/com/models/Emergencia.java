package com.tbd.ejemplo1.models;
import java.util.Date;

public class Emergencia{
    private Integer id;
    private String nombre;
    private String descrip;
    private Date finicio;
    private Date ffin;
    private Integer id_institucion;

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

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Date getFinicio() {
        return finicio;
    }

    public void setFinicio(Date finicio) {
        this.finicio = finicio;
    }

    public Date getFfin() {
        return ffin;
    }

    public void setFfin(Date ffin) {
        this.ffin = ffin;
    }

    public Integer getIdInstitucion() {
        return id_institucion;
    }

    public void setIdInstitucion(Integer id_institucion) {
        this.id_institucion = id_institucion;
    }

}
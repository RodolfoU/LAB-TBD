package cl.tbd.voluntariado.models;

import java.util.Date;

public class Voluntario {
    private Integer id;
    private String nombre;
    private Date fnacimiento;

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

    public Date getfNacimiento() {
        return fnacimiento;
    }

    public void setfNacimiento(Date fnacimiento) {
        this.fnacimiento = fnacimiento;
    }

}

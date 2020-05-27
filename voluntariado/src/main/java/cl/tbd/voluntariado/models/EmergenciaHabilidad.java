package cl.tbd.voluntariado.models;

public class EmergenciaHabilidad {
    private Integer id;
    private Integer id_emergencia;
    private Integer id_habilidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEmergencia() {
        return id_emergencia;
    }

    public void setIdEmergencia(Integer id_emergencia) {
        this.id_emergencia = id_emergencia;
    }

    public Integer getIdHabilidad() {
        return id_habilidad;
    }

    public void setIdHabilidad(Integer id_habilidad) {
        this.id_habilidad = id_habilidad;
    }
}

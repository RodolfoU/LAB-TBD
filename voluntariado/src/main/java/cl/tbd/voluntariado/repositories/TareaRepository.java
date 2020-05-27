package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Tarea;

import java.util.List;

public interface TareaRepository {
    public List<Tarea> getAllTar();
    public List<Tarea> getTarForEmer(String emergencia);

}
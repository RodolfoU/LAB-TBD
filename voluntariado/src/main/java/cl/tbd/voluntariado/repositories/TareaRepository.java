package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Tarea;

import java.util.List;

public interface TareaRepository {
    public List<Tarea> getAllTar();
    public List<Tarea> getTar(long id);
    public List<Tarea> getTarForEmer(String emergencia);
    public Tarea createTar(Tarea tar);
    public void deleteTar(long id);
    public Tarea updateTar(Tarea tar);
}

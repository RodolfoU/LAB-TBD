package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Tarea;

import java.util.List;

public interface TareaRepository {
    public Tarea createTar(Tarea tar);
    public String deleteTar(long id);

    public Tarea updateTar(Tarea tar);

    public List<Tarea> getAllTar();
    public List<Tarea> getTar(long id);
    public List<Tarea> getTarForEmer(String emergencia);
    public int getNumeroTareas() ;
    }

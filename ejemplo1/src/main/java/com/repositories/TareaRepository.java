package com.tbd.ejemplo1.repositories;
import com.tbd.ejemplo1.models.Tarea;
import java.util.List;

public interface TareaRepository {
    public List<Tarea> getAllTar();
    public List<Tarea> getTarForEmer(String emergencia);
}

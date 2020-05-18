package com.tbd.ejemplo1.repositories;
import java.util.List;
import com.tbd.ejemplo1.models.Institucion;

public interface InstitucionRepository {
    public List<Institucion> getAllInst();
    public List<Institucion> getInstForId(long id);
}
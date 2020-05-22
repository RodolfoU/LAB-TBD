package com.tbd.ejemplo1.repositories;
import java.util.List;
import com.tbd.ejemplo1.models.Voluntario;
import java.util.ArrayList;


public interface VoluntarioRepository {
    public List<Voluntario> getAllVol();
    public int countVoluntarios();
    public List<Voluntario> getVolForHab(String habilidad);
    public List<Voluntario> getVolForEme();
}
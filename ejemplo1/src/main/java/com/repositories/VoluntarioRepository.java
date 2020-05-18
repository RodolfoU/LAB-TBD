package com.tbd.ejemplo1.repositories;
import java.util.List;
import com.tbd.ejemplo1.models.Voluntario;

public interface VoluntarioRepository {
    public List<Voluntario> getAllVol();
    public int countVoluntarios();
}
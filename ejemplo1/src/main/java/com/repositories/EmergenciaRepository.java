package com.tbd.ejemplo1.repositories;
import com.tbd.ejemplo1.models.Emergencia;
import java.util.List;

public interface EmergenciaRepository {
    public List<Emergencia> getAllEmer();
    public List<Emergencia> getEmerForId(long id);
    public List<Emergencia> getEmerForInst(String institucion);
}

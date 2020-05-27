package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;

import java.util.List;

public interface EmergenciaRepository {
    public List<Emergencia> getAllEmer();
    public List<Emergencia> getEmerForId(long id);
    public List<Emergencia> getEmerForInst(String institucion);
    public Emergencia createEmer(Emergencia emer);
    public void deleteEmer(long id);
    public Emergencia putEmer(Emergencia emer);
}

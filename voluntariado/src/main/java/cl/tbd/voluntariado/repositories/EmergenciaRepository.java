package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Geometry;

import java.util.List;

public interface EmergenciaRepository {
    public Emergencia createEmer(Emergencia emer);
    public String deleteEmer(long id);

    public Emergencia putEmer(Emergencia emer);

    public List<Emergencia> getAllEmer();
    public List<Emergencia> getEmerForId(long id);
    public List<Emergencia> getEmerForInst(String institucion);

    public int contTuplas (String querySQL);
    public List<Geometry> getEmerPuntos();


}

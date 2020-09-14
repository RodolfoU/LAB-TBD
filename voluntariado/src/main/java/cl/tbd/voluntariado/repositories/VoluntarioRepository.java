package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Geometry;
import cl.tbd.voluntariado.models.Voluntario;

import java.awt.*;
import java.util.List;

public interface VoluntarioRepository {
    public Voluntario createVoluntario(Voluntario vol);
    public String deleteVoluntario(long id);

    public Voluntario updateVoluntario(Voluntario vol);

    public List<Voluntario> getAllVol();
    public List<Voluntario> getVoluntario(long id);
    public int countVoluntarios();
    public List<Voluntario> getVolForHab(String habilidad);
    public List<Voluntario> getVolForEme();
    public List<Voluntario> getVolForTar(String tarea);
    public List<Voluntario> getVolForIntervRank(long inferior,long superior);

    public int contTuplas (String querySQL);

    public List<Geometry> getVolPuntos();

    public List<Geometry> getVolCercanoEmer(Integer idEmer, long radio);

}

package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Habilidad;

import java.util.List;

public interface HabildadRepository {
    public Habilidad createHab(Habilidad hab);
    public List<Habilidad> getAllHab();
    public List<Habilidad> getHabForId(long id);
    public Habilidad putHab(Habilidad hab);
    public void deleteHab(long id);
}

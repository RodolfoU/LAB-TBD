package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Institucion;

import java.util.List;

public interface InstitucionRepository {

    public List<Institucion> getAllInst();
    public List<Institucion> getInstForId(long id);

    public int contTuplas (String querySQL);
}

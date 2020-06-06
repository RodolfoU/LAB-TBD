package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Voluntario;

import java.util.List;

public interface VoluntarioRepository {
    public List<Voluntario> getAllVol();
    public int countVoluntarios();
    public List<Voluntario> getVolForHab(String habilidad);
    public List<Voluntario> getVolForEme();
    public List<Voluntario> getVolForTar(String tarea);
    public List<Voluntario> getVolForIntervRank(long inferior,long superior);
    public List<Voluntario> getVoluntario(long id);
    public Voluntario createVoluntario(Voluntario vol);
    public String deleteVoluntario(long id);
    public Voluntario updateVoluntario(Voluntario vol);
    public String inscribirVol(long idVol,long idTar);
    public int validarVoluntarioEnTarea(long idVol,long idTar);
    public void updateTarCantidadInscritos(long idTar);
    }

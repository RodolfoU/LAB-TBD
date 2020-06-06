package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Tarea;
import cl.tbd.voluntariado.models.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class VoluntarioRepositoryImp implements VoluntarioRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Voluntario> getAllVol() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from voluntario").executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int countVoluntarios() {
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery("SELECT COUNT(*) FROM voluntario").executeScalar(Integer.class);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return total;
    }

    @Override
    public List<Voluntario> getVolForHab(String habilidad){
        habilidad = Character.toUpperCase(habilidad.charAt(0)) +  habilidad.substring(1);
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT DISTINCT v.id,v.nombre,v.fnacimiento FROM voluntario AS v, vol_habilidad AS vh, habilidad AS h WHERE v.id = vh.id_voluntario AND h.id = vh.id_habilidad AND h.descrip = '" + habilidad +"' order by v.id").executeAndFetch(Voluntario.class);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Voluntario> getVolForEme(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select DISTINCT c.id,c.nombre,c.fnacimiento from tarea a, ranking b, voluntario c, emergencia e where a.id = b.id_tarea and b.id_voluntario = c.id and e.id = a.id_emergencia ").executeAndFetch(Voluntario.class);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Voluntario> getVolForTar(String tarea){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select distinct v.id, v.nombre, v.fnacimiento from ranking r, voluntario v, tarea tar where r.id_voluntario = v.id and r.id_tarea = tar.id and tar.nombre = '"+tarea+"' order by v.id").executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Voluntario> getVolForIntervRank(long inferior,long superior){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select v.id, v.nombre, v.fnacimiento from ranking r, voluntario v where r.id_voluntario = v.id and r.puntaje between "+inferior+" and "+superior).executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Voluntario> getVoluntario(long id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from voluntario v where v.id = " + id).executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Voluntario createVoluntario(Voluntario vol){
        String querySql = "insert into voluntario(id,nombre,fnacimiento) values (:volid,:volnombre,:volfnacimiento)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql)
                    .addParameter("volid",vol.getId())
                    .addParameter("volnombre",vol.getNombre())
                    .addParameter("volfnacimiento",vol.getfNacimiento())
                    .executeUpdate();
            return vol;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String deleteVoluntario(long id){
        String querySql = "delete from voluntario where id =" + id;
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql).executeUpdate();
            return "Se ha eliminado un voluntario";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Voluntario updateVoluntario(Voluntario vol){
        String querySql = "update voluntario set nombre=:volnombre, fnacimiento=:volfnacimiento where id=:volid";
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql)
                    .addParameter("volnombre",vol.getNombre())
                    .addParameter("volfnacimiento",vol.getfNacimiento())
                    .addParameter("volid",vol.getId())
                    .executeUpdate();
            return vol;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int validarVoluntarioEnTarea(long idVol,long idTar) {
        int cont;
        String querySql = "select count (n.id) as cantidad from " +
                "(select h.id,h.descrip from habilidad h, tarea t, tarea_habilidad th,eme_habilidad eh " +
                "where h.id = eh.id_habilidad and eh.id = th.id_emehab and th.id_tarea = t.id and t.id = '" + idTar + "' and t.cant_vol_requeridos > t.cant_vol_inscritos " +
                "intersect " +
                "select distinct h.id, h.descrip " +
                "from voluntario v, vol_habilidad vh, habilidad h " +
                "where v.id = vh.id_voluntario and vh.id_habilidad = h.id and v.id = '" + idVol + "') n";
        try(Connection conn = sql2o.open()){
            cont = conn.createQuery(querySql).executeAndFetchFirst(Integer.class);
            return cont;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public void updateTarCantidadInscritos(long idTar){
        String querySql = "update tarea set cant_vol_inscritos = cant_vol_inscritos  + 1 where id =" + idTar;
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql).executeUpdate();
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    @Override
    public String inscribirVol(long idVol,long idTar) {
        Integer cont;
        cont = validarVoluntarioEnTarea(idVol, idTar);
        if (cont > 0) {
            String querySql = "insert into ranking (id,id_voluntario,id_tarea,puntaje,flg_invitado,flg_participa) values(:id,:idVol,:idTar,:puntj,:flgInvitado,:flgParticipa)";
            try (Connection conn = sql2o.open()) {
                conn.createQuery(querySql)
                        .addParameter("id", 10000)
                        .addParameter("idVol", idVol)
                        .addParameter("idTar", idTar)
                        .addParameter("puntj", 50)
                        .addParameter("flgInvitado", 1)
                        .addParameter("flgParticipa", 1)
                        .executeUpdate();
                updateTarCantidadInscritos(idTar);
                return "El voluntario se ha inscrito en la tarea";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "No se pudo agregar al voluntario";
            }
        } else {
            return "El voluntario no se puede inscribir en la tarea";
        }
    }
}


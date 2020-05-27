package cl.tbd.voluntariado.repositories;

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
    public void deleteVoluntario(long id){
        String querySql = "delete from voluntario where id =" + id;
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql).executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
}

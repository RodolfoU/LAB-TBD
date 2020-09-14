package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Geometry;
import cl.tbd.voluntariado.models.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

@Repository
public class VoluntarioRepositoryImp implements VoluntarioRepository {
    @Autowired
    private Sql2o sql2o;

    //---------------------------------------------------------------------------------------------------------------------------
    //------------------------------- REST SERVICES -----------------------------------------------------------------------------

    // POST (create) ------------------------------------------------------------------

    /*
   Entrada: Recibe un objeto tipo Voluntario
   Proceso: Se encarga de hacer un create (crea un voluntario en la base de datos)
   Salida: Retorna el objeto creado
    */
    @Override
    public Voluntario createVoluntario(Voluntario vol) {
        int contVol;
        String querySql = "insert into voluntario(id,nombre,fnacimiento) values (:volid,:volnombre,:volfnacimiento)";
        contVol = contTuplas("select MAX(id) from voluntario");
        try (Connection conn = sql2o.open()) {
            conn.createQuery(querySql)
                    .addParameter("volid", contVol + 1)
                    .addParameter("volnombre", vol.getNombre())
                    .addParameter("volfnacimiento", vol.getfNacimiento())
                    .executeUpdate();
            vol.setId(contVol + 1);
            return vol;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //DELETE -------------------------------------------------------------------------

    /*
   Entrada: Recibe un dato tipo long que representa una id
   Proceso: Se encarga de hacer un delete (borra un voluntario en la base de datos)
   Salida: Retorna un string con el mensaje de respuesta de la id eliminada
    */
    @Override
    public String deleteVoluntario(long id) {
        String querySql = "delete from voluntario where id =" + id;
        try (Connection conn = sql2o.open()) {
            conn.createQuery(querySql).executeUpdate();
            return "Se ha eliminado el voluntario " + id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //PUTS (set) --------------------------------------------------------------------

    /*
   Entrada: Recibe un objeto tipo Voluntario
   Proceso: Se encarga de hacer un update (modifica un voluntario en la base de datos)
   Salida: Retorna el objeto modificado
    */
    @Override
    public Voluntario updateVoluntario(Voluntario vol) {
        String querySql = "update voluntario set nombre=:volnombre, fnacimiento=:volfnacimiento where id=:volid";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(querySql)
                    .addParameter("volnombre", vol.getNombre())
                    .addParameter("volfnacimiento", vol.getfNacimiento())
                    .addParameter("volid", vol.getId())
                    .executeUpdate();
            return vol;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // GETS (select) -------------------------------------------------------------------------------------

    /*
   Entrada: No recibe nada
   Proceso: Se guardan los voluntarios de la base de datos en una lista
   Salida: Retorna una lista de objetos tipo voluntario
    */
    @Override
    public List<Voluntario> getAllVol() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select * from voluntario ").executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
   Entrada: Recibe un dato tipo long que representa una id
   Proceso: Se encarga de guardar una lista con objetos tipo voluntarios
   Salida: Retorna la lista con objetos tipo voluntarios
    */
    @Override
    public List<Voluntario> getVoluntario(long id) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select * from voluntario v where v.id = " + id).executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
   Entrada: No recibe nada
   Proceso: Se encarga de contar la cantidad de voluntarios en la base de datos
   Salida: Retorna un valor entero con la cantidad de voluntarios
    */
    @Override
    public int countVoluntarios() {
        int total = 0;
        try (Connection conn = sql2o.open()) {
            total = conn.createQuery("SELECT COUNT(*) FROM voluntario").executeScalar(Integer.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return total;
    }

    /*
   Entrada: Recibe un string con el nombre de la habilidad
   Proceso: Se encarga de buscar a los voluntarios que tienen una habilidad especifica
   Salida: Retorna una lista con los objetos tipo voluntarios
    */
    @Override
    public List<Voluntario> getVolForHab(String habilidad) {
        habilidad = Character.toUpperCase(habilidad.charAt(0)) + habilidad.substring(1);
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("SELECT DISTINCT v.id,v.nombre,v.fnacimiento FROM voluntario AS v, vol_habilidad AS vh, habilidad AS h WHERE v.id = vh.id_voluntario AND h.id = vh.id_habilidad AND h.descrip = '" + habilidad + "' order by v.id").executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
   Entrada: No recibe nada
   Proceso: Se encarga de buscar a los voluntarios asociados a todas las emergencia (todos los que estan ya inscritos a una emergencia)
   Salida: Retorna una lista con los objetos tipo voluntario
    */
    @Override
    public List<Voluntario> getVolForEme() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select DISTINCT c.id,c.nombre,c.fnacimiento from tarea a, ranking b, voluntario c, emergencia e where a.id = b.id_tarea and b.id_voluntario = c.id and e.id = a.id_emergencia ").executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
  Entrada: Recibe un string con el nombre de una tarea
  Proceso: Se encarga de buscar a los voluntarios asociados a la tarea
  Salida: Retorna una lista con los objetos tipo voluntario
   */
    @Override
    public List<Voluntario> getVolForTar(String tarea) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select distinct v.id, v.nombre, v.fnacimiento from ranking r, voluntario v, tarea tar where r.id_voluntario = v.id and r.id_tarea = tar.id and tar.nombre = '" + tarea + "' order by v.id").executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
  Entrada: Recibe dos datos tipo long que representan los rangos del puntaje a buscar
  Proceso: Busca a los voluntarios que tengan un puntaje dentro del intervalo
  Salida: Retorna una lista de objetos tipo voluntarios
   */
    @Override
    public List<Voluntario> getVolForIntervRank(long inferior, long superior) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select v.id, v.nombre, v.fnacimiento from ranking r, voluntario v where r.id_voluntario = v.id and r.puntaje between " + inferior + " and " + superior).executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //OTROS ----------------------------------------------------------------------------------------------------------------------

    /*
  Entrada: Recibe un string con una consulta sql
  Proceso: Se encarga de contar la cantidad elementos de una consulta
  Salida: Retorna un entero con el numero elementos de la respuesta de la base de datos
   */
    @Override
    public int contTuplas(String querySQL) {
        int total = 0;
        try (Connection conn = sql2o.open()) {
            total = conn.createQuery(querySQL).executeScalar(Integer.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return total;
    }

    @Override
    public List<Geometry> getVolPuntos() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select ST_X(location) as longitud, ST_Y(location) as latitud from voluntario ").executeAndFetch(Geometry.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Geometry> coordenadasEmergencia(Integer idEmer){
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select id , nombre, ST_X(location) as longitud, ST_Y(location) as latitud from emergencia where id ="+idEmer).executeAndFetch(Geometry.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Geometry> getVolCercanoEmer(Integer idEmer,long radio) {
        try (Connection conn = sql2o.open()) {
            List<Geometry> emer = coordenadasEmergencia(idEmer);
            String query = "select v.id, v.nombre, ST_X(v.location) as longitud, ST_Y(v.location) as latitud " +
                    "from voluntario v " +
                    "where st_distance(ST_GeomFromText('POINT(" + emer.get(0).getLongitud() + " " + emer.get(0).getLatitud() + ")', 4326), v.location::geography) < " + radio;
            return conn.createQuery(query).executeAndFetch(Geometry.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}


package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class EmergenciaRepositoryImp implements EmergenciaRepository{
    @Autowired
    private Sql2o sql2o;

    //---------------------------------------------------------------------------------------------------------------------------
    //------------------------------- REST SERVICES -----------------------------------------------------------------------------

    // POST (create) --------------------------------------------

    /*
  Entrada: Recibe un objetivo tipo emergencia
  Proceso: Se encarga de realizar un create (crea un voluntario en la base de datos)
  Salida: Retorna un objeto tipo emergencia
   */
    @Override
    public Emergencia createEmer(Emergencia emer){
        int contEmer;
        String insert_sql = "insert into emergencia (id,nombre,descrip,finicio,ffin,id_institucion,promedio) values " +
                "(:emerId,:emerName,:emerDescrip,:emerFinicio,:emerFfin,:emerId_institucion, :promedio)";
        contEmer = contTuplas("select MAX(id) from emergencia");
        try(Connection conn = sql2o.open()){
            conn.createQuery(insert_sql)
                    .addParameter("emerId",contEmer+1)
                    .addParameter("emerName",emer.getNombre())
                    .addParameter("emerDescrip",emer.getDescrip())
                    .addParameter("emerFinicio",emer.getFinicio())
                    .addParameter("emerFfin",emer.getFfin())
                    .addParameter("emerId_institucion",emer.getIdInstitucion())
                    .addParameter("promedio",emer.getPromedio())
                    .executeUpdate();
            emer.setId(contEmer+1);
            return emer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // DELETE ----------------------------------------------

    /*
  Entrada: Recibe un dato tipo long que representa una id
  Proceso: Se encarga de hacer un delete (borra una emergencia de la base de datos)
  Salida: Retorna un string con el mensaje de respuesto de la id de emergencia
   */
    @Override
    public String deleteEmer(long id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from emergencia e where e.id = "+id)
                    .executeUpdate();
            return "Se ha eliminado la emergencia "+id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // PUT(update) ----------------------------------------------

    /*
  Entrada: Recibe un objeto tipo emergencia
  Proceso: Se encarga de hacer un update (modifica una emergencia en la base de datos)
  Salida: Retorna el objeto modificado
   */
    @Override
    public Emergencia putEmer(Emergencia emer){
        String updateSql = "update emergencia set nombre = :emerName, descrip = :emerDescrip, " +
                "finicio = :emerFinicio, ffin = :emerFfin, id_institucion = :idinst , promedio=:prom where id = :emerId";
        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                    .addParameter("emerName",emer.getNombre())
                    .addParameter("emerDescrip", emer.getDescrip())
                    .addParameter("emerFinicio",emer.getFinicio())
                    .addParameter("emerFfin",emer.getFfin())
                    .addParameter("emerId",emer.getId())
                    .addParameter("idinst",emer.getIdInstitucion())
                    .addParameter("prom",emer.getPromedio())
                    .executeUpdate();
            return emer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // GETS (SELECT) ----------------------------------------------

    /*
 Entrada: No recibe nada
 Proceso: Se encarga de mostrar todas las emergencias de la base de datos
 Salida: Retorna una lista con los objetos
  */
    @Override
    public List<Emergencia> getAllEmer() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from emergencia").executeAndFetch(Emergencia.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
 Entrada: Recibe un dato tipo long que representa una id
 Proceso: Se encarga de buscar una emergecia por id
 Salida: Retorna un arreglo con el objeto emergencia
  */
    @Override
    public List<Emergencia> getEmerForId(long id) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from emergencia i where i.id="+id).executeAndFetch(Emergencia.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
 Entrada: Recibe un string con el nombre de la institucion
 Proceso: Se encarga de mostrar todas las emergencias asociadas a una institucion
 Salida: Retorna una lista de objetos
  */
    @Override
    public List<Emergencia> getEmerForInst(String institucion) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select e.id,e.nombre,e.descrip,e.finicio,e.ffin,e.id_institucion from emergencia e,institucion i where e.id_institucion = i.id and i.nombre = '"+institucion+"'").executeAndFetch(Emergencia.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //OTROS ----------------------------------------------------------------------------------------------------------------------

    /*
 Entrada: Recibe un string con la consulta sql
 Proceso: Se encarga de contar los elementos de una consulta de sql
 Salida: Retorna un entero con la cantidad de tuplas de la respuesta
  */
    @Override
    public int contTuplas (String querySQL){
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery(querySQL).executeScalar(Integer.class);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return total;
    }

    @Override
    public List<Geometry> getEmerPuntos() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select id , nombre, ST_X(location) as longitud, ST_Y(location) as latitud from emergencia").executeAndFetch(Geometry.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

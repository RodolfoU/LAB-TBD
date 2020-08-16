package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class TareaRepositoryImp implements TareaRepository{
    @Autowired
    private Sql2o sql2o;

    //---------------------------------------------------------------------------------------------------------------------------
    //------------------------------- REST SERVICES -----------------------------------------------------------------------------

    //CREATE --------------------------------------------------------------------------------------------------------------------

    /*
 Entrada: Recibe un objeto tipo tarea
 Proceso: Se encarga de hacer un create (crea una tarea en la base de datos)
 Salida: Retorna el objeto creado
  */
    @Override
    public Tarea createTar(Tarea tar){
        int contTar;
        String querySql = "insert into tarea(id,nombre,descrip,cant_vol_requeridos,cant_vol_inscritos,id_emergencia,finicio,ffin,id_estado) " +
                "values (:tarId,:tarNombre,:tarDescrip,:tarCantRequer,:tarCantInscrit,:tarId_Emer,:tarFinicio,:tarFfin,:tarId_estado)";
        contTar = contTuplas("select MAX(id) from tarea");
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql)
                    .addParameter("tarId",contTar+1)
                    .addParameter("tarNombre",tar.getNombre())
                    .addParameter("tarDescrip",tar.getDescrip())
                    .addParameter("tarCantRequer",tar.getCantVolRequeridos())
                    .addParameter("tarCantInscrit",tar.getCantVolInscritos())
                    .addParameter("tarId_Emer",tar.getIdEmergencia())
                    .addParameter("tarFinicio",tar.getFinicio())
                    .addParameter("tarFfin",tar.getFfin())
                    .addParameter("tarId_estado",tar.getIdEstado())
                    .executeUpdate();
            tar.setId(contTar+1);
            return tar;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //DELETE --------------------------------------------------------------------------------------------------------------------

    /*
 Entrada: Recibe un dato tipo long que representa un id
 Proceso: Se encarga de hacer un delete (borra una emergencia en la base de datos)
 Salida: Retorna el objeto borrado
  */
    @Override
    public String deleteTar(long id){
        String querySql = "delete from tarea where id =" + id;
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql).executeUpdate();
            return "Se ha eliminado la tarea "+id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //PUTS ----------------------------------------------------------------------------------------------------------------------

    /*
 Entrada: Recibe un objeto tipo tarea
 Proceso: Se encarga de hacer un update (modifica una tarea en la base de datos)
 Salida: Retorna el objeto modificado
  */
    @Override
    public Tarea updateTar(Tarea tar){
        String querySql = "update tarea set nombre = :tarNombre,descrip = :tarDescrip,cant_vol_requeridos = :tarCantRequer," +
                "cant_vol_inscritos = :tarCantInscrit,id_emergencia = :tarId_Emer,finicio = :tarFinicio, ffin = :tarFfin,id_estado = :tarId_estado where id = :tarId";
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql)
                    .addParameter("tarId",tar.getId())
                    .addParameter("tarNombre",tar.getNombre())
                    .addParameter("tarDescrip",tar.getDescrip())
                    .addParameter("tarCantRequer",tar.getCantVolRequeridos())
                    .addParameter("tarCantInscrit",tar.getCantVolInscritos())
                    .addParameter("tarId_Emer",tar.getIdEmergencia())
                    .addParameter("tarFinicio",tar.getFinicio())
                    .addParameter("tarFfin",tar.getFfin())
                    .addParameter("tarId_estado",tar.getIdEstado())
                    .executeUpdate();
            return tar;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //GETS ----------------------------------------------------------------------------------------------------------------------

     /*
  Entrada: No recibe nada
  Proceso: Se encarga de mostrar todas las tareas de la base de datos
  Salida: Retorna una lista con todos los objetos tipo tara de la base de datos
   */
    @Override
    public List<Tarea> getAllTar() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from tarea").executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
 Entrada: Recibe un dato tipo long que representa una id
 Proceso: Se encarga de seleccionar una tarea por id
 Salida: Retorna el objeto seleccionado
  */
    @Override
    public List<Tarea> getTar(long id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from tarea t where t.id = " + id).executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
 Entrada: Recibe un string con el nombre de la emergencia
 Proceso: Se encarga de mostrar todas las tareas asociadas a una emergencia en particular
 Salida: Retorna una lista con objetos tipo tarea
  */
    @Override
    public List<Tarea> getTarForEmer(String emergencia) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select tar.id,tar.nombre,tar.descrip,tar.cant_vol_requeridos,tar.cant_vol_inscritos,tar.id_emergencia,tar.finicio,tar.ffin,tar.id_estado from tarea tar, emergencia emer where tar.id_emergencia = emer.id and emer.nombre = '"+emergencia+"'").executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
 Entrada: No recibe nada
 Proceso: Se encarga de contar la cantidad de tareas que hay registradas en la base de datos
 Salida: Retorna un entero con el numero de tareas
  */
    @Override
    public int getNumeroTareas() {
        int cont;
        try(Connection conn = sql2o.open()){
           cont =conn.createQuery("select count(*) from tarea").executeScalar(Integer.class);
           return cont;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    //OTROS ----------------------------------------------------------------------------------------------------------------------

    /*
 Entrada: Recibe un string con la consulta sql
 Proceso: Se encarga de contar la cantidad de elementos de una respuesta de la base de datos
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
}

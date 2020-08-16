package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class HablidadRepositoryImp implements HabildadRepository{
    @Autowired
    private Sql2o sql2o;

    //---------------------------------------------------------------------------------------------------------------------------
    //------------------------------- REST SERVICES -----------------------------------------------------------------------------

    // POST (create) --------------------------------------------------------------------------------------

     /*
  Entrada: Recibe un objeto tipo haiblidad
  Proceso: Se encarga de hacer un create (creauna emergencia en la base de datos)
  Salida: Retorna el objeto modificado
   */
    @Override
    public Habilidad createHab(Habilidad hab) {
        int contHab;
        String insert_sql = "insert into habilidad (id,descrip) values (:habId,:habDescrip)";
        contHab = contTuplas("select MAX(id) from habilidad");
        try(Connection conn = sql2o.open()){
            conn.createQuery(insert_sql)
                    .addParameter("habId",contHab+1)
                    .addParameter("habDescrip",hab.getDescrip())
                    .executeUpdate();
            hab.setId(contHab+1);
            return hab;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // DELETE ---------------------------------------------------------------------------------------------

    /*
 Entrada: Recibe un dato tipo long que representa una id
 Proceso: Se encarga de hacer un delete (borra una emergencia en la base de datos)
 Salida: Retorna un string con el mensaje de respuesta de la habilidad borrada y su id
  */
    @Override
    public String deleteHab(long id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from habilidad hab where hab.id = "+id)
                    .executeUpdate();
            return "Se ha eliminado la habilidad "+id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // PUTS (set) -----------------------------------------------------------------------------------------

    /*
 Entrada: Recibe un objeto tipo habilidad
 Proceso: Se encarga de hacer un update(modifica una emergencia en la base de datos)
 Salida: Retorna el objeto modificado
  */
    @Override
    public Habilidad putHab(Habilidad hab){
        String updateSql = "update habilidad set descrip = :habDescrip where id = :habId";
        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                    .addParameter("habId",hab.getId())
                    .addParameter("habDescrip",hab.getDescrip())
                    .executeUpdate();
            return hab;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // GETS (select)

    /*
 Entrada: No recibe nada
 Proceso: Se encarga de mostrar todas las habilidades que hay en la base de datos
 Salida: Retorna una lista con los objetos tipo habilidad
  */
    @Override
    public List<Habilidad> getAllHab(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from habilidad").executeAndFetch(Habilidad.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
Entrada: Recibe un dato tipo long que representa una id
Proceso: Se encarga de mostrar la habilidad segun el id
Salida: Retorna una lista con un objeto tipo habilidad
 */
    @Override
    public List<Habilidad> getHabForId(long id) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from habilidad i where i.id="+id).executeAndFetch(Habilidad.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //OTROS ----------------------------------------------------------------------------------------------------------------------

    /*
Entrada: Recibe un string con la consulta sql
Proceso: Se encarga de contar la cantidad de tuplas de una respuesta a una consulta en la base de datos
Salida: Retorna un entero con la cantidad de tuplas contadas
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

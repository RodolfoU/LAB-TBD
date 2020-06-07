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
    @Override
    public Habilidad createHab(Habilidad hab) {
        String insert_sql = "insert into habilidad (id,descrip) values (:habId,:habDescrip)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(insert_sql)
                    .addParameter("habId",hab.getId())
                    .addParameter("habDescrip",hab.getDescrip())
                    .executeUpdate();
            return hab;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // DELETE ---------------------------------------------------------------------------------------------
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
    @Override
    public List<Habilidad> getAllHab(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from habilidad").executeAndFetch(Habilidad.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Habilidad> getHabForId(long id) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from habilidad i where i.id="+id).executeAndFetch(Habilidad.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

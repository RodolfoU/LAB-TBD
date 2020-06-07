package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
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
    @Override
    public Emergencia createEmer(Emergencia emer){
        String insert_sql = "insert into emergencia (id,nombre,descrip,finicio,ffin,id_institucion) values " +
                "(:emerId,:emerName,:emerDescrip,:emerFinicio,:emerFfin,:emerId_institucion)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(insert_sql)
                    .addParameter("emerId",emer.getId())
                    .addParameter("emerName",emer.getNombre())
                    .addParameter("emerDescrip",emer.getDescrip())
                    .addParameter("emerFinicio",emer.getFinicio())
                    .addParameter("emerFfin",emer.getFfin())
                    .addParameter("emerId_institucion",emer.getIdInstitucion())
                    .executeUpdate();
            return emer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // DELETE ----------------------------------------------
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
    @Override
    public Emergencia putEmer(Emergencia emer){
        String updateSql = "update emergencia set nombre = :emerName, descrip = :emerDescrip, " +
                "finicio = :emerFinicio, ffin = :emerFfin, id_institucion = :idinst where id = :emerId";
        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                    .addParameter("emerName",emer.getNombre())
                    .addParameter("emerDescrip", emer.getDescrip())
                    .addParameter("emerFinicio",emer.getFinicio())
                    .addParameter("emerFfin",emer.getFfin())
                    .addParameter("emerId",emer.getId())
                    .addParameter("idinst",emer.getIdInstitucion())
                    .executeUpdate();
            return emer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // GETS (SELECT) ----------------------------------------------
    @Override
    public List<Emergencia> getAllEmer() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from emergencia").executeAndFetch(Emergencia.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Emergencia> getEmerForId(long id) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from emergencia i where i.id="+id).executeAndFetch(Emergencia.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Emergencia> getEmerForInst(String institucion) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select e.id,e.nombre,e.descrip,e.finicio,e.ffin,e.id_institucion from emergencia e,institucion i where e.id_institucion = i.id and i.nombre = '"+institucion+"'").executeAndFetch(Emergencia.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

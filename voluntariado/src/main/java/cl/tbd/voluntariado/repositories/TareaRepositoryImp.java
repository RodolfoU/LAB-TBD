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

    @Override
    public List<Tarea> getAllTar() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from tarea").executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Tarea> getTar(long id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from tarea t where t.id = " + id).executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Tarea> getTarForEmer(String emergencia) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select tar.id,tar.nombre, tar.descrip,tar.cant_vol_requeridos,tar.cant_vol_inscritos,tar.finicio,tar.ffin,tar.id_estado from tarea tar, emergencia emer where tar.id_emergencia = emer.id and emer.nombre = '"+emergencia+"'").executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tarea createTar(Tarea tar){
        String querySql = "insert into tarea(id,nombre,descrip,cant_vol_requeridos,cant_vol_inscritos,id_emergencia,finicio,ffin,id_estado) " +
                "values (:tarId,:tarNombre,:tarDescrip,:tarCantRequer,:tarCantInscrit,:tarId_Emer,:tarFinicio,:tarFfin,:tarId_estado)";
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

    @Override
    public void deleteTar(long id){
        String querySql = "delete from tarea where id =" + id;
        try(Connection conn = sql2o.open()){
            conn.createQuery(querySql).executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
}

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

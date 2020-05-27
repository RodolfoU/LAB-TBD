package cl.tbd.voluntariado.repositories;

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

    @Override
    public List<Habilidad> getAllHab(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from habilidad").executeAndFetch(Habilidad.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

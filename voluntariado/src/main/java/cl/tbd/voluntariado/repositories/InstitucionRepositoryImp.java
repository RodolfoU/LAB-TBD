package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Institucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class InstitucionRepositoryImp implements InstitucionRepository{
    @Autowired
    private Sql2o sql2o;

    //---------------------------------------------------------------------------------------------------------------------------
    //------------------------------- REST SERVICES -----------------------------------------------------------------------------

    //GETS (select) -------------------------------------------------------------------------------------------------------------
    @Override
    public List<Institucion> getAllInst() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from institucion").executeAndFetch(Institucion.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Institucion> getInstForId(long id) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from institucion i where i.id="+id).executeAndFetch(Institucion.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //OTROS ----------------------------------------------------------------------------------------------------------------------
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

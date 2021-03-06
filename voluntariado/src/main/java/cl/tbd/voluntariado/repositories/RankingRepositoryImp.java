package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class RankingRepositoryImp implements RankingRepository {
    @Autowired
    private Sql2o sql2o;

    //---------------------------------------------------------------------------------------------------------------------------
    //------------------------------- REST SERVICES -----------------------------------------------------------------------------

    //CREATE --------------------------------------------------------------------------------------------------------------------

    /*
    Entrada: Recibe un objeto tipo ranking
    Proceso: Se encarga de hacer un create (crea un ranking en la base de datos)
    Salida: Retorna el objeto creado
     */
    @Override
    public Ranking createRank(Ranking rank){
        int aux, contRank;
        aux = validateVolinTar(rank.getIdVoluntario(),rank.getIdTarea());
        if (aux > 0) {
            String insert_sql = "insert into ranking (id,id_voluntario,id_tarea,puntaje,flg_invitado,flg_participa) " +
                    "values (:rankId,:rankId_voluntario,:rankId_tarea,:rankPuntaje,:rankFlg_invitado,:rankFlg_participa)";
            contRank = contTuplas("select MAX(id) from ranking");
            try (Connection conn = sql2o.open()) {
                conn.createQuery(insert_sql)
                        .addParameter("rankId", contRank+1)
                        .addParameter("rankId_voluntario", rank.getIdVoluntario())
                        .addParameter("rankId_tarea", rank.getIdTarea())
                        .addParameter("rankPuntaje", rank.getPuntaje())
                        .addParameter("rankFlg_invitado", rank.getFlgInvitado())
                        .addParameter("rankFlg_participa", rank.getFlgParticipa())
                        .executeUpdate();

                conn.createQuery("update tarea set cant_vol_inscritos = cant_vol_inscritos + 1 where id =" + rank.getIdTarea()).executeUpdate();

                rank.setId(contRank+1);
                return rank;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        else{
            return null;
        }
    }

    //DELETE --------------------------------------------------------------------------------------------------------------------

    /*
   Entrada: Recibe un dato tipo long que representa un id
   Proceso: Se encarga de hacer un delete (borra un ranking en la base de datos)
   Salida: Retorna un string que es el mensaje de respuesta a la peticion
    */
    @Override
    public String deleteRank(long id){
        long idTar;
        try(Connection conn = sql2o.open()){
            idTar = conn.createQuery("select id_tarea from ranking where id = "+id).executeScalar(Integer.class);
            conn.createQuery("update tarea set cant_vol_inscritos = cant_vol_inscritos - 1 where id =" + idTar).executeUpdate();
            conn.createQuery("delete from ranking r where r.id = "+id).executeUpdate();
            return "Se ha eliminado el ranking "+id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //PUTS (set) ----------------------------------------------------------------------------------------------------------------

    /*
   Entrada: Recibe una objeto tipo ranking
   Proceso: Se encarga de hacer un update (modifica un ranking en la base de datos)
   Salida: Retorna el objeto creado
    */
    @Override
    public Ranking putRank(Ranking rank){
        String updateSql = "update ranking set id_voluntario = :rankId_voluntario, id_tarea = :rankId_tarea, puntaje = :rankPuntaje, flg_invitado = :rankFlg_invitado, flg_participa = :rankFlg_participa where id = :rankId";
        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                    .addParameter("rankId_voluntario",rank.getIdVoluntario())
                    .addParameter("rankId_tarea", rank.getIdTarea())
                    .addParameter("rankPuntaje",rank.getPuntaje())
                    .addParameter("rankFlg_invitado",rank.getFlgInvitado())
                    .addParameter("rankFlg_participa",rank.getFlgParticipa())
                    .addParameter("rankId",rank.getId())
                    .executeUpdate();
            return rank;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //GETS (select) -------------------------------------------------------------------------------------------------------------

     /*
    Entrada: No recibe nada
    Proceso: Se encarga de entregar todos los rankings de la base de datos
    Salida: Retorna una lista de objetos
     */
    @Override
    public List<Ranking> getAllRank(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from ranking").executeAndFetch(Ranking.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
   Entrada: Recibe un dato tipo long que representa un id
   Proceso: Se encarga de buscar una tupla de ranking por id
   Salida: Retorna una lista con un objeto
    */
    @Override
    public List<Ranking> getRankForId(long id) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from ranking r where r.id="+id).executeAndFetch(Ranking.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //OTROS ----------------------------------------------------------------------------------------------------------------------

    /*
   Entrada: Recibe un string con la consulta SQL
   Proceso: Se encarga de contar la cantidad de elementos de una consulta
   Salida: Retorna un entero con el numero de elementos
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

    /*
   Entrada: Recibe un dato tipo long que representa un id del voluntario,  y otro id de la tarea
   Proceso: Se encarga de ver que habilidades hay en comun, entre la tarea y el voluntario
   Salida: Retorna un entero con la cantidad de habilidades en comun
    */
    @Override
    public int validateVolinTar(long idVol,long idTar) {
        String querySql = "select count (n.id) as cantidad from " +
                "(select h.id,h.descrip from habilidad h, tarea t, tarea_habilidad th,eme_habilidad eh " +
                "where h.id = eh.id_habilidad and eh.id = th.id_emehab and th.id_tarea = t.id and t.id = '" + idTar +
                "' and t.cant_vol_requeridos > t.cant_vol_inscritos " +
                "intersect " +
                "select distinct h.id, h.descrip " +
                "from voluntario v, vol_habilidad vh, habilidad h " +
                "where v.id = vh.id_voluntario and vh.id_habilidad = h.id and v.id = '" + idVol + "') n";
        return contTuplas(querySql);
    }
}
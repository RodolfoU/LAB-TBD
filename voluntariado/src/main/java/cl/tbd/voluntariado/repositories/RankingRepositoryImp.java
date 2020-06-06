package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
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

    @Override
    public List<Ranking> getAllRank(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from ranking").executeAndFetch(Ranking.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Ranking> getRankForId(long id) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from ranking r where r.id="+id).executeAndFetch(Ranking.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Ranking createRank(Ranking rank){
        String insert_sql = "insert into ranking (id,id_voluntario,id_tarea,puntaje,flg_invitado,flg_participa) values (:rankId,:rankId_voluntario,:rankId_tarea,:rankPuntaje,:rankFlg_invitado,:rankFlg_participa)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(insert_sql)
                    .addParameter("rankId",rank.getId())
                    .addParameter("rankId_voluntario",rank.getIdVoluntario())
                    .addParameter("rankId_tarea",rank.getIdTarea())
                    .addParameter("rankPuntaje",rank.getPuntaje())
                    .addParameter("rankFlg_invitado",rank.getFlgInvitado())
                    .addParameter("rankFlg_participa",rank.getFlgParticipa())
                    .executeUpdate();
            return rank;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteRank(long id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from ranking r where r.id = "+id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
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
}
package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Ranking;

import java.util.List;

public interface RankingRepository {
    public List<Ranking> getAllRank();
    public List<Ranking> getRankForId(long id);
    public Ranking createRank(Ranking rank);
    public void deleteRank(long id);
    public Ranking putRank(Ranking rank);
}
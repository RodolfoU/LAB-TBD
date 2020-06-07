package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Ranking;

import java.util.List;

public interface RankingRepository {
    public Ranking createRank(Ranking rank);
    public String deleteRank(long id);

    public Ranking putRank(Ranking rank);

    public List<Ranking> getAllRank();
    public List<Ranking> getRankForId(long id);
}
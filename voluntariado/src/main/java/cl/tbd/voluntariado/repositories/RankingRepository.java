package cl.tbd.voluntariado.repositories;

import cl.tbd.voluntariado.models.Ranking;

import java.util.List;

public interface RankingRepository {
    public Ranking createRank(Ranking rank);
    public String deleteRank(long id);

    public Ranking putRank(Ranking rank);

    public List<Ranking> getAllRank();
    public List<Ranking> getRankForId(long id);

    public int contTuplas (String querySQL);
    public int validateVolinTar(long idVol,long idTar);
}
package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Ranking;
import cl.tbd.voluntariado.repositories.RankingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RankingService {

    private final RankingRepository rankRepository;

    public RankingService(RankingRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @GetMapping("/rankings")
    @ResponseBody
    public List<Ranking> getAllRank(){
        return rankRepository.getAllRank();
    }

    @GetMapping("/rankings/id={id}")
    @ResponseBody
    public List<Ranking> getRankForId(@PathVariable long id){
        return rankRepository.getRankForId(id);
    }

    @PostMapping("/rankings")
    @ResponseBody
    public Ranking createRank(@RequestBody Ranking rank){
        Ranking result = rankRepository.createRank(rank);
        return result;
    }

    @DeleteMapping("/rankings/delete/id={id}")
    @ResponseBody
    public void  deleteRank(@PathVariable long id){
        rankRepository.deleteRank(id);
    }

    @PutMapping("/rankings/update")
    @ResponseBody
    public Ranking putRank(@RequestBody Ranking rank){
        Ranking result = rankRepository.putRank(rank);
        return result;
    }
}
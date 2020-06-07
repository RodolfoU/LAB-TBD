package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Ranking;
import cl.tbd.voluntariado.repositories.RankingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RankingService {

    private final RankingRepository rankRepository;

    //MÉTODO CONSTRUCTOR ---------------------------------------------------------------------------------
    public RankingService(RankingRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    //CREATE --------------------------------------------------------------------------------------------
    @PostMapping("/rankings")
    @ResponseBody
    public Ranking createRank(@RequestBody Ranking rank){
        Ranking result = rankRepository.createRank(rank);
        return result;
    }

    //DELETE --------------------------------------------------------------------------------------------
    @DeleteMapping("/rankings/delete/id={id}")
    @ResponseBody
    public String deleteRank(@PathVariable long id){
        return rankRepository.deleteRank(id);
    }

    //PUTS ----------------------------------------------------------------------------------------------
    @PutMapping("/rankings/update")
    @ResponseBody
    public Ranking putRank(@RequestBody Ranking rank){
        Ranking result = rankRepository.putRank(rank);
        return result;
    }

    //GETS ----------------------------------------------------------------------------------------------
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

}
package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Habilidad;
import cl.tbd.voluntariado.repositories.HabildadRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HabilidadService {

    private final HabildadRepository habRepository;

    //MÉTODO CONSTRUCTOR -------------------------------------------------------------------------
    public HabilidadService(HabildadRepository habRepository) {
        this.habRepository = habRepository;
    }

    //CREATE -------------------------------------------------------------------------------------
    @PostMapping("/habilidades")
    @ResponseBody
    public Habilidad createHab(@RequestBody Habilidad hab){
        Habilidad result = habRepository.createHab(hab);
        return result;
    }

    //DELETE -------------------------------------------------------------------------------------
    @DeleteMapping("/habilidades/delete/id={id}")
    @ResponseBody
    public String  deleteHab(@PathVariable long id){
        return habRepository.deleteHab(id);
    }

    //PUTS ---------------------------------------------------------------------------------------
    @PutMapping("/habilidades/update")
    @ResponseBody
    public Habilidad putHab(@RequestBody Habilidad hab){
        Habilidad result = habRepository.putHab(hab);
        return result;
    }

    //GETS ---------------------------------------------------------------------------------------
    @GetMapping("/habilidades")
    @ResponseBody
    public List<Habilidad> getAllHab(){
        return habRepository.getAllHab();
    }

    @GetMapping("/habilidades/id={id}")
    @ResponseBody
    public List<Habilidad> getHabForId(@PathVariable long id){ return habRepository.getHabForId(id); }

}

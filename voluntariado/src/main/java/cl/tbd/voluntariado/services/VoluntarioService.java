package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Voluntario;
import cl.tbd.voluntariado.repositories.VoluntarioRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class VoluntarioService {
    private final VoluntarioRepository volRepository;

    //METODO CONSTRUCTOR -------------------------------------------------------------------
    VoluntarioService(VoluntarioRepository volRepository){
        this.volRepository = volRepository;
    }

    //CREATE --------------------------------------------------------------------------------
    @CrossOrigin(origins="*")
    @PostMapping("/voluntarios")
    @ResponseBody
    public Voluntario createVoluntario(@RequestBody Voluntario vol){
        return volRepository.createVoluntario(vol);
    }

    //DELETE --------------------------------------------------------------------------------
    @DeleteMapping("/voluntarios/delete/id={id}")
    @ResponseBody
    public String deleteVoluntario(@PathVariable long id){
        return volRepository.deleteVoluntario(id);
    }

    //PUTS ----------------------------------------------------------------------------------
    @PutMapping("/voluntarios/update")
    @ResponseBody
    public Voluntario updateVoluntario(@RequestBody Voluntario vol){
        return volRepository.updateVoluntario(vol);
    }

    //GETS ----------------------------------------------------------------------------------
    @CrossOrigin(origins="*")
    @GetMapping("/voluntarios")
    @ResponseBody
    public List<Voluntario> getAllVol() {
        return volRepository.getAllVol();
    }

    @CrossOrigin(origins="*")
    @GetMapping("/voluntarios/id={id}")
    @ResponseBody
    public List<Voluntario> getVoluntario(@PathVariable long id){
        return volRepository.getVoluntario(id);
    }

    @CrossOrigin(origins="*")
    @GetMapping("/voluntarios/count")
    @ResponseBody
    public String countVoluntarios(){
        int total = volRepository.countVoluntarios();
        return String.format("Tienes %s voluntarios!!", total);
    }

    @CrossOrigin(origins="*")
    @GetMapping("/voluntarios/hab={habilidad}")
    @ResponseBody
    public List<Voluntario> getVolForHab(@PathVariable String habilidad) {
        return volRepository.getVolForHab(habilidad);
    }

    @CrossOrigin(origins="*")
    @GetMapping("/voluntarios/emergencias")
    @ResponseBody
    public List<Voluntario> getVolForEme() {
        return volRepository.getVolForEme();
    }

    @CrossOrigin(origins="*")
    @GetMapping("/voluntarios/tar={tarea}")
    @ResponseBody
    public List<Voluntario> getVolForTar(@PathVariable String tarea){
        return volRepository.getVolForTar(tarea);
    }

    @CrossOrigin(origins="*")
    @GetMapping("/voluntarios/pt>{inferior}&pt<{superior}")
    @ResponseBody
    public List<Voluntario> getVolForIntervRank(@PathVariable long inferior, @PathVariable long superior){
        return volRepository.getVolForIntervRank(inferior,superior);
    }

}

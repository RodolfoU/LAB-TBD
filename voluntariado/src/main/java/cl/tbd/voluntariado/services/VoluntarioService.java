package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Voluntario;
import cl.tbd.voluntariado.repositories.VoluntarioRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class VoluntarioService {
    private final VoluntarioRepository volRepository;
    VoluntarioService(VoluntarioRepository volRepository){
        this.volRepository = volRepository;
    }

    @GetMapping("/voluntarios")
    @ResponseBody
    public List<Voluntario> getAllVol() {
        return volRepository.getAllVol();
    }

    @GetMapping("/voluntarios/count")
    @ResponseBody
    public String countVoluntarios(){
        int total = volRepository.countVoluntarios();
        return String.format("Tienes %s voluntarios!!", total);
    }

    @GetMapping("/voluntarios/hab={habilidad}")
    @ResponseBody
    public List<Voluntario> getVolForHab(@PathVariable String habilidad) {
        return volRepository.getVolForHab(habilidad);
    }

    @GetMapping("/voluntarios/emergencias")
    @ResponseBody
    public List<Voluntario> getVolForEme() {
        return volRepository.getVolForEme();
    }

    @GetMapping("/voluntarios/tar={tarea}")
    @ResponseBody
    public List<Voluntario> getVolForTar(@PathVariable String tarea){
        return volRepository.getVolForTar(tarea);
    }

    @GetMapping("/voluntarios/pt>{inferior}&pt<{superior}")
    @ResponseBody
    public List<Voluntario> getVolForIntervRank(@PathVariable long inferior, @PathVariable long superior){
        return volRepository.getVolForIntervRank(inferior,superior);
    }

    @GetMapping("/voluntarios/id={id}")
    @ResponseBody
    public List<Voluntario> getVoluntario(@PathVariable long id){
        return volRepository.getVoluntario(id);
    }

    @PostMapping("/voluntarios")
    @ResponseBody
    public Voluntario createVoluntario(@RequestBody Voluntario vol){
        return volRepository.createVoluntario(vol);
    }

    @DeleteMapping("/voluntarios/delete/id={id}")
    @ResponseBody
    public void deleteVoluntario(@PathVariable long id){
         volRepository.deleteVoluntario(id);
    }

    @PutMapping("voluntarios/update")
    @ResponseBody
    public Voluntario updateVoluntario(@RequestBody Voluntario vol){
        return volRepository.updateVoluntario(vol);
    }

}

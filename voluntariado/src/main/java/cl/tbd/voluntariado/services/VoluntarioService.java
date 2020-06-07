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

    @GetMapping("/voluntarios/inscrib/nom={idVol}&tar={idTar}")
    @ResponseBody
    public String inscribirVol(@PathVariable long idVol,@PathVariable long idTar){
        return volRepository.inscribirVol(idVol,idTar);
    }

    //GETS ----------------------------------------------------------------------------------
    @GetMapping("/voluntarios")
    @ResponseBody
    public List<Voluntario> getAllVol() {
        return volRepository.getAllVol();
    }

    @GetMapping("/voluntarios/id={id}")
    @ResponseBody
    public List<Voluntario> getVoluntario(@PathVariable long id){
        return volRepository.getVoluntario(id);
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

}

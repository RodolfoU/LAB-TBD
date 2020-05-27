package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.repositories.EmergenciaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmergenciaService {
    private final EmergenciaRepository emerRepository;

    public EmergenciaService(EmergenciaRepository emerRepository) {
        this.emerRepository = emerRepository;
    }

    @GetMapping("/emergencias")
    @ResponseBody
    public List<Emergencia> getAllEmer(){
        return emerRepository.getAllEmer();
    }

    @GetMapping("/emergencias/id={id}")
    @ResponseBody
    public List<Emergencia> getEmerForId(@PathVariable long id){
        return emerRepository.getEmerForId(id);
    }

    @GetMapping("/emergencias/inst={institucion}")
    @ResponseBody
    public List<Emergencia> getEmerForInst(@PathVariable String institucion){
        return emerRepository.getEmerForInst(institucion);
    }

    @PostMapping("/emergencias")
    @ResponseBody
    public Emergencia createEmer(@RequestBody Emergencia emer){
        Emergencia result = emerRepository.createEmer(emer);
        return result;
    }

    @DeleteMapping("/emergencias/delete/id={id}")
    @ResponseBody
    public void  deleteEmer(@PathVariable long id){
        emerRepository.deleteEmer(id);
    }

    @PutMapping("/emergencias/update")
    @ResponseBody
    public Emergencia putEmer(@RequestBody Emergencia emer){
        Emergencia result = emerRepository.putEmer(emer);
        return result;
    }
}

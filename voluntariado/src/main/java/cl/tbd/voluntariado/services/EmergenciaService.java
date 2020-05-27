package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.repositories.EmergenciaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("emergencias/inst={institucion}")
    @ResponseBody
    public List<Emergencia> getEmerForInst(@PathVariable String institucion){
        return emerRepository.getEmerForInst(institucion);
    }
}

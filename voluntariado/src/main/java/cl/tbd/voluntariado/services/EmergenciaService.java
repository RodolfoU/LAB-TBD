package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Emergencia;
import cl.tbd.voluntariado.models.Geometry;
import cl.tbd.voluntariado.repositories.EmergenciaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmergenciaService {
    private final EmergenciaRepository emerRepository;

    //Método Constructor ---------------------------------------------------------------------------------

    public EmergenciaService(EmergenciaRepository emerRepository) {
        this.emerRepository = emerRepository;
    }

    //CREATE ---------------------------------------------------------------------------------------------

    @PostMapping("/emergencias")
    @ResponseBody
    public Emergencia createEmer(@RequestBody Emergencia emer){
        Emergencia result = emerRepository.createEmer(emer);
        return result;
    }

    //DELETE ---------------------------------------------------------------------------------------------
    @DeleteMapping("/emergencias/delete/id={id}")
    @ResponseBody
    public String deleteEmer(@PathVariable long id){
        return emerRepository.deleteEmer(id);
    }

    //PUTS -----------------------------------------------------------------------------------------------
    @PutMapping("/emergencias/update")
    @ResponseBody
    public Emergencia putEmer(@RequestBody Emergencia emer){
        Emergencia result = emerRepository.putEmer(emer);
        return result;
    }

    //GETS -----------------------------------------------------------------------------------------------
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

    @CrossOrigin(origins = "*")
    @GetMapping("/emergencias/puntos")
    @ResponseBody
    public List<Geometry> getEmerPuntos(){
        return emerRepository.getEmerPuntos();
    }

}

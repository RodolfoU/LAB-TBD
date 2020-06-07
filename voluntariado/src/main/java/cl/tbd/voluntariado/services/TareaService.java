package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Tarea;
import cl.tbd.voluntariado.repositories.TareaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TareaService {
    private final TareaRepository tarRepository;

    //MÉTODO CONSTRUCTOR ---------------------------------------------------------------------------
    public TareaService(TareaRepository tarRepository) {
        this.tarRepository = tarRepository;
    }

    //CREATE ---------------------------------------------------------------------------------------
    @PostMapping("/tareas")
    @ResponseBody
    public Tarea createTar(@RequestBody Tarea tar){
        return tarRepository.createTar(tar);
    }

    //DELETE ---------------------------------------------------------------------------------------
    @DeleteMapping("/tareas/delete/id={id}")
    @ResponseBody
    public String deleteTar(@PathVariable long id){
        return tarRepository.deleteTar(id);
    }

    //PUTS -----------------------------------------------------------------------------------------
    @PutMapping("tareas/update")
    @ResponseBody
    public Tarea updateTar(@RequestBody Tarea tar){
        return tarRepository.updateTar(tar);
    }

    //GETS -----------------------------------------------------------------------------------------
    @GetMapping("/tareas")
    @ResponseBody
    public List<Tarea> getAllTar(){
        return tarRepository.getAllTar();
    }

    @GetMapping("/tareas/emer={emergencia}")
    @ResponseBody
    public List<Tarea> getTarForEmer(@PathVariable String emergencia){
        return tarRepository.getTarForEmer(emergencia);
    }

    @GetMapping("/tareas/id={id}")
    @ResponseBody
    public List<Tarea> getTar(@PathVariable long id){
        return tarRepository.getTar(id);
    }

}

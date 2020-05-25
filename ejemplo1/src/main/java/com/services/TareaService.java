package com.tbd.ejemplo1.services;

import com.tbd.ejemplo1.models.Tarea;
import com.tbd.ejemplo1.repositories.TareaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TareaService {

    private final TareaRepository tarRepository;

    public TareaService(TareaRepository tarRepository) {
        this.tarRepository = tarRepository;
    }

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
}

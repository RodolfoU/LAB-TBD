package com.tbd.ejemplo1.services;

import com.tbd.ejemplo1.models.Voluntario;
import com.tbd.ejemplo1.repositories.VoluntarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import java.util.ArrayList;

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

}
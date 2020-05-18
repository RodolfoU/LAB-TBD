package com.tbd.ejemplo1.services;

import com.tbd.ejemplo1.models.Voluntario;
import com.tbd.ejemplo1.repositories.VoluntarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
package com.tbd.ejemplo1.services;

import com.tbd.ejemplo1.models.Institucion;
import com.tbd.ejemplo1.repositories.InstitucionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@RestController
public class InstitucionService {

    private final InstitucionRepository instRepository;
    InstitucionService(InstitucionRepository instRepository){
        this.instRepository = instRepository;
    }

    @GetMapping("/instituciones")
    @ResponseBody
    public List<Institucion> getAllInst() {
           return instRepository.getAllInst();
    }

    @GetMapping("/instituciones/id={id}")
    @ResponseBody
    public List<Institucion> getInstForId(@PathVariable long id) {
        return instRepository.getInstForId(id);
    }
}
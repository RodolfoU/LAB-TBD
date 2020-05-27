package cl.tbd.voluntariado.services;

import cl.tbd.voluntariado.models.Habilidad;
import cl.tbd.voluntariado.repositories.HabildadRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HabilidadService {

    private final HabildadRepository habRepository;

    public HabilidadService(HabildadRepository habRepository) {
        this.habRepository = habRepository;
    }

    @GetMapping("/habilidades")
    @ResponseBody
    public List<Habilidad> getAllHab(){
        return habRepository.getAllHab();
    }
}

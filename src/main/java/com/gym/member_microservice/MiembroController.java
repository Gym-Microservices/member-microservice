package com.gym.member_microservice;

import com.gym.member_microservice.model.Miembro;
import com.gym.member_microservice.service.MiembroService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/miembro")
public class MiembroController {

    private final MiembroService miembroService;

    @PostMapping("/miembros")
    public Miembro registrarMiembro(@RequestBody Miembro miembro) {
        return miembroService.registrarMiembro(miembro);
    }

    @GetMapping("/miembros")
    public List<Miembro> obtenerTodosMiembros() {
        return miembroService.obtenerTodosMiembros();
    }

}

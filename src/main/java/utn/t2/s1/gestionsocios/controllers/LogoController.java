package utn.t2.s1.gestionsocios.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.servicios.LogoServicio;

import java.io.IOException;


// Esta clase es un controlador REST que maneja las peticiones relacionadas con el logo
@RestController
@RequestMapping("/logo")
@CrossOrigin(origins = "*")
public class LogoController {

    // Se inyecta el servicio de logo
    @Autowired
    private LogoServicio logoService;

    // Este método muestra una imagen de un logo por su nombre
    @GetMapping("/{imageName}")
    public void showImage(@PathVariable String imageName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logoService.showLogo(imageName, response);

    }

}

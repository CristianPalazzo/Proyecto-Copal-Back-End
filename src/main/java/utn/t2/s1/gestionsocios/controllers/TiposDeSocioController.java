package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.dtos.AtributosDTO;
import utn.t2.s1.gestionsocios.excepciones.TipoException;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.servicios.TipoSocioServicio;

@Tag(name = "Operaciones para los los tipos de los socios", description = "Api para realizar las operaciones de alta, baja y modificacion de tipos de socios")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@RequestMapping("/tipodesocio")
@Validated
@CrossOrigin
public class TiposDeSocioController {

    @Autowired
    TipoSocioServicio tipoSocioServicio;

    @GetMapping(value = {"/", ""})
    @Operation(summary = "Retorna todos los nombres de los tipo de socios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Object> verNombresTipos(){
        return new ResponseEntity<>(tipoSocioServicio.tiposDesocio(), HttpStatus.OK);
    }

    @PostMapping(value = {"/", ""})
    @Operation(summary = "Agrega un tipo de socio en la Base de datos")
    public ResponseEntity<Object> agregarTipoSocio(@RequestBody @Valid AtributosDTO categoriaDTO) throws TipoException {
        if(tipoSocioServicio.buscarPorNombre(categoriaDTO.getNombre()) != null){
            return new ResponseEntity<>("El nombre '"+ categoriaDTO.getNombre()+"' de tipo ya existe", HttpStatus.CREATED);
        }
        TipoSocio _tipo = new TipoSocio();
        _tipo.setNombre(categoriaDTO.getNombre());
        _tipo.setEstado(Estado.ACTIVO);
        tipoSocioServicio.agregar(_tipo);

        return new ResponseEntity<>(_tipo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un tipo de socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de socio eliminado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo de socio no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Object> eliminarTipo(@PathVariable Long id){
        tipoSocioServicio.borrar(id);
        return new ResponseEntity<>("Tipo de socio eliminado", HttpStatus.OK);
    }



}

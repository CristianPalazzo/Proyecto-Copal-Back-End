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
import utn.t2.s1.gestionsocios.converters.TipoDeUsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.TipoDeUsuarioDTO;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.servicios.TipoDeUsuarioServicio;
import java.util.List;



@Tag(name = "Operaciones para los tipo de usuarios", description = "Api para realizar las operaciones de alta, baja y modificacion de un tipo de usuario")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@RequestMapping("/tipodeusuario")
@Validated
@CrossOrigin
public class TipoDeUsuarioController {

    @Autowired
    TipoDeUsuarioServicio tipoDeUsuarioServicio;

    @Autowired
    TipoDeUsuarioConverter tipoDeUsuarioConverter;


    @GetMapping()
    @Operation(summary = "Retorna todos los tipo de usuario de la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuario encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<List<TipoDeUsuario>> verTiposDeUsuarios(){

        List<TipoDeUsuario> tipoDeUsuarios = tipoDeUsuarioServicio.buscarTodos();
        return new ResponseEntity<>(tipoDeUsuarios , HttpStatus.OK);
    }




    @PostMapping()
    @Operation(summary = "Agrega un tipo de usuario en la Base de datos")
    public ResponseEntity<Object> agregarTipoDeUsuario(@RequestBody @Valid TipoDeUsuarioDTO tipoDeUsuarioDTO){
        if(tipoDeUsuarioServicio.buscarPorNombre(tipoDeUsuarioDTO.getNombreTipoDeUsuario()) != null){
            return new ResponseEntity<>("El nombre '"+ tipoDeUsuarioDTO.getNombreTipoDeUsuario()+"' de tipo de usuario ya existe", HttpStatus.CREATED);
        }

        TipoDeUsuario tipoDeUsuario = tipoDeUsuarioServicio.agregar(tipoDeUsuarioDTO);

        return new ResponseEntity<>(tipoDeUsuario, HttpStatus.CREATED);
    }




    @PutMapping("/{id}")
    @Operation(summary = "Modifica un tipo de usuario en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuario modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo de usuario no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarTipoDeUsuario(@PathVariable Long id, @RequestBody TipoDeUsuarioDTO tipoDeUsuarioDTO){
        try {
            TipoDeUsuario tipoDeUsuarioUpdate = tipoDeUsuarioServicio.actualizar(tipoDeUsuarioDTO, id);
            return new ResponseEntity<>(tipoDeUsuarioUpdate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping(value = {"/{id}", "/{id}/"})
    @Operation(summary = "Elimina un tipo de usuario en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuario eliminado" ,content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> deleteTipoDeUsuario(@PathVariable Long id){
        try {
            tipoDeUsuarioServicio.eliminar(id);
            return new ResponseEntity<>("Tipo de usuario eliminado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }


}

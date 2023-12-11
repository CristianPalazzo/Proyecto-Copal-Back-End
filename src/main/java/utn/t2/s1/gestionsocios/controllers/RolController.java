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
import utn.t2.s1.gestionsocios.converters.RolConverter;
import utn.t2.s1.gestionsocios.converters.TipoDeUsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.dtos.TipoDeUsuarioDTO;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.servicios.RolServicio;
import utn.t2.s1.gestionsocios.servicios.TipoDeUsuarioServicio;

import java.util.List;


@Tag(name = "Operaciones para los roles", description = "Api para realizar las operaciones de alta, baja y modificacion de roles")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema(allOf = Rol.class)) })
})
@RestController
@RequestMapping("/roles")
@Validated
@CrossOrigin
public class RolController {


    @Autowired
    RolServicio rolServicio;

    @Autowired
    RolConverter rolConverter;



    @GetMapping()
    @Operation(summary = "Retorna los roles de la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tipo de rol encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Rol.class)) }),
    })
    public ResponseEntity<List<Rol>> verRoles(){

        List<Rol> roles = rolServicio.buscarTodos();
        return new ResponseEntity<>(roles , HttpStatus.OK);
    }


    @PostMapping()
    @Operation(summary = "Agrega un rol a la Base de datos")
    public ResponseEntity<Object> agregarRol(@RequestBody @Valid RolDTO rolDTO){
        if(rolServicio.buscarPorNombre(rolDTO.getNombre()) != null){
            return new ResponseEntity<>("El rol '"+ rolDTO.getNombre()+"' ya existe", HttpStatus.CREATED);
        }

        Rol rol = rolServicio.agregar(rolDTO);

        return new ResponseEntity<>(rol, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifica un rol en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El rol no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarRol(@PathVariable Long id, @RequestBody RolDTO rolDTO){
            Rol rolUpdate = rolServicio.actualizar(rolDTO, id);
            return new ResponseEntity<>(rolUpdate , HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{id}", "/{id}/"})
    @Operation(summary = "Elimina un Rol en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado" ,content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> deleteRol(@PathVariable Long id){
            rolServicio.eliminar(id);
            return new ResponseEntity<>("Rol eliminado", HttpStatus.OK);
    }


}

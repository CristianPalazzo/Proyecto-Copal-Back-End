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
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.servicios.CategoriaServicio;

@Tag(name = "Operaciones para las categorías", description = "Api para realizar las operaciones de alta, baja y modificacion de categorías")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@RequestMapping("/categorias")
@Validated
@CrossOrigin
public class CategoriasController {


    @Autowired
    CategoriaServicio categoriaServicio;


    @GetMapping(value = {"/", ""})
    @Operation(summary = "Retorna todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Object> verNombresCategorias(){
        return new ResponseEntity<>(categoriaServicio.categorias(), HttpStatus.OK);
    }

    @PostMapping(value = {"/", ""})
    @Operation(summary = "Agrega una categoría en la Base de datos")
    public ResponseEntity<Object> agregarCategoria(@RequestBody @Valid AtributosDTO categoriaDTO){ //TODO
        if(categoriaServicio.buscarPorNombre(categoriaDTO.getNombre()) != null){
            return new ResponseEntity<>("El nombre '"+ categoriaDTO.getNombre()+"' de categoria ya existe", HttpStatus.CREATED);
        }
        Categoria _categoria = new Categoria();
        _categoria.setNombre(categoriaDTO.getNombre());
        _categoria.setEstado(Estado.ACTIVO);
        categoriaServicio.agregar(_categoria);

        return new ResponseEntity<>(_categoria, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una categoria en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria eliminada" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Object> eliminarCategoria(@PathVariable Long id){
        categoriaServicio.borrar(id);
        return new ResponseEntity<>("Categoria eliminada", HttpStatus.OK);
    }




}

package utn.t2.s1.gestionsocios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.TipoDeUsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.TipoDeUsuarioDTO;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.TipoDeUsuarioRepo;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDeUsuarioServicio {

    @Autowired
    TipoDeUsuarioRepo tipoDeUsuarioRepository;

    @Autowired
    TipoDeUsuarioConverter tipoDeUsuarioConverter;


    public List<TipoDeUsuario> buscarTodos() {
        return tipoDeUsuarioRepository.findAllByEstado(Estado.ACTIVO) ;
    }


    public TipoDeUsuario buscarPorNombre(String nombre) {
        return tipoDeUsuarioRepository.findByTipoAndEstado(nombre, Estado.ACTIVO);
    }


    public TipoDeUsuario agregar(TipoDeUsuarioDTO tipoDeUsuarioDto) {
        TipoDeUsuario tipoDeUsuario = tipoDeUsuarioConverter.toUsuario(tipoDeUsuarioDto);

        tipoDeUsuario.setEstado(Estado.ACTIVO);

        try {
            tipoDeUsuario = tipoDeUsuarioRepository.save(tipoDeUsuario);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de violación de restricción única (nombre duplicado)
            //throw new RuntimeException("El nombre de usuario ya existe en la base de datos.");
            throw new IllegalArgumentException("El nombre del tipo de usuario ya existe en la base de datos.");
        }

        return tipoDeUsuario;
    }




    public TipoDeUsuario actualizar(TipoDeUsuarioDTO tipoDeUsuarioDto, long id) {
        Optional<TipoDeUsuario> optionalTipoDeUsuario = tipoDeUsuarioRepository.findById(id);
        if (!optionalTipoDeUsuario.isPresent()) {
            throw new RuntimeException("Tipo de usuario no encontrado");
        }
        TipoDeUsuario tipoDeUsuarioUpdate = optionalTipoDeUsuario.get();
        tipoDeUsuarioUpdate.setTipo(tipoDeUsuarioDto.getNombreTipoDeUsuario());


        tipoDeUsuarioUpdate = tipoDeUsuarioRepository.save(tipoDeUsuarioUpdate);

        return tipoDeUsuarioUpdate;
    }


    public void eliminar(long id) {

        Optional<TipoDeUsuario> optionalTipoDeUsuario = tipoDeUsuarioRepository.findById(id);
        if (!optionalTipoDeUsuario.isPresent() || optionalTipoDeUsuario.get().getEstado() == Estado.ELIMINADO) {
            throw new RuntimeException("Tipo de Usuario no encontrada");
        }

        TipoDeUsuario tipoDeUsuario = optionalTipoDeUsuario.get();

        tipoDeUsuario.setEstado(Estado.ELIMINADO);

        tipoDeUsuarioRepository.save(tipoDeUsuario);
    }




}


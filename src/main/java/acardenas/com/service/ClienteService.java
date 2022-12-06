package acardenas.com.service;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Cliente;

import java.util.Optional;

public interface ClienteService {

    Optional<Cliente> obtenerPorCodigo(String codigo) ;

    boolean registrar(Cliente cliente) throws ValidationException;

    boolean actualizar(Cliente cliente) throws ValidationException;
}

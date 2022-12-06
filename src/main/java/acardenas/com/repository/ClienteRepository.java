package acardenas.com.repository;

import acardenas.com.model.Cliente;

import java.util.Optional;

public interface ClienteRepository {

    Optional<Cliente> obtenerPorCodigo(String codigo);

    Optional<Integer> registrar(Cliente cliente);

    Optional<Integer> actualizar(Cliente cliente);
}

package acardenas.com.service.impl;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Cliente;
import acardenas.com.repository.ClienteRepository;
import acardenas.com.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Optional<Cliente> obtenerPorCodigo(String codigo){
        return clienteRepository.obtenerPorCodigo(codigo);
    }

    @Override
    public boolean registrar(Cliente cliente) throws ValidationException {

        if (obtenerPorCodigo(cliente.getCodigoUnico()).isPresent()){
           throw new ValidationException("¡El cliente ya existe!");
        }

        return clienteRepository.registrar(cliente).isPresent();
    }

    @Override
    public boolean actualizar(Cliente cliente) throws ValidationException {

        if (!Optional.ofNullable(cliente.getCodigoUnico()).isPresent()){
            throw new ValidationException("¡No ha enviado el código del usuario!");
        }

        if (!obtenerPorCodigo(cliente.getCodigoUnico()).isPresent()){
            throw new ValidationException(String.format("¡No existe el Cliente con el código %s!",
                    cliente.getCodigoUnico()));
        }

        return clienteRepository.actualizar(cliente).isPresent();
    }
}

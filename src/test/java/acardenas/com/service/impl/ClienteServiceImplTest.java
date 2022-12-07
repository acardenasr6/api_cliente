package acardenas.com.service.impl;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Cliente;
import acardenas.com.repository.ClienteRepository;
import acardenas.com.util.DatosTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void testObtenerCliente(){
        String codigoUnico = DatosTest.CLIENTE.getCodigoUnico();
        when(clienteRepository.obtenerPorCodigo(any(String.class))).thenReturn(Optional.of(DatosTest.CLIENTE));
        Optional<Cliente> cliente = clienteService.obtenerPorCodigo(codigoUnico);
        assertNotNull(cliente);
        assert(cliente.isPresent());
        assertEquals(codigoUnico, cliente.get().getCodigoUnico());
        verify(clienteRepository).obtenerPorCodigo(any(String.class));
    }

    @Test
    void testRegistrarCliente() throws ValidationException {
        when(clienteRepository.registrar(any(Cliente.class))).thenReturn(Optional.of(1));
        boolean cliente = clienteService.registrar(DatosTest.CLIENTE);
        assert(cliente);
        verify(clienteRepository).registrar(any(Cliente.class));
    }

    @Test
    void testRegistrarClienteException() {
        when(clienteRepository.obtenerPorCodigo(any(String.class))).thenReturn(Optional.of(DatosTest.CLIENTE));
        try {
          clienteService.registrar(DatosTest.CLIENTE);
        } catch (Exception e) {
            assertTrue(e instanceof ValidationException);
            assertEquals(DatosTest.MESSAGE_EXCEPTION_CLIENTE_EXISTENTE, e.getMessage());
        }
        verify(clienteRepository).obtenerPorCodigo(any(String.class));
    }

    @Test
    void testActualizarCliente() throws ValidationException {
        when(clienteRepository.obtenerPorCodigo(anyString())).thenReturn(Optional.of(DatosTest.CLIENTE));
        when(clienteRepository.actualizar(any(Cliente.class))).thenReturn(Optional.of(1));
        boolean cliente = clienteService.actualizar(DatosTest.CLIENTE_PARA_ACTUALIZAR);
        assert(cliente);
        verify(clienteRepository).actualizar(any(Cliente.class));
        verify(clienteRepository).obtenerPorCodigo(any(String.class));
    }

    @Test
    void testActualizarClienteException() {
        when(clienteRepository.obtenerPorCodigo(anyString())).thenReturn(Optional.empty());
        try {
            clienteService.actualizar(DatosTest.CLIENTE);
        } catch (Exception e) {
            assertTrue(e instanceof ValidationException);
            assertEquals(
                    String.format(
                            DatosTest.MESSAGE_EXCEPTION_CLIENTE_NO_EXISTE,
                            DatosTest.CLIENTE.getCodigoUnico()),
                    e.getMessage());
        }
        verify(clienteRepository).obtenerPorCodigo(any(String.class));
    }

    @Test
    void testActualizarClienteExceptionCodigoUnico() {
        try {
            clienteService.actualizar(DatosTest.CLIENTE_SIN_CODIGO_UNICO);
        } catch (Exception e) {
            assertTrue(e instanceof ValidationException);
            assertEquals(DatosTest.MESSAGE_EXCEPTION_CLIENTE_REQUERIDO, e.getMessage());
        }
    }

}
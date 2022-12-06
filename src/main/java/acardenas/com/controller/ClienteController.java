package acardenas.com.controller;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Cliente;
import acardenas.com.service.ClienteService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPoCodigo(@RequestParam String codigo){
        return ResponseEntity
                .ok()
                .body(clienteService.obtenerPorCodigo(codigo));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registrar(@RequestBody Cliente cliente) throws ValidationException {
        return ResponseEntity
                .ok()
                .body(clienteService.registrar(cliente));
    }

    @PutMapping (consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Cliente cliente) throws ValidationException {
        return ResponseEntity
                .ok()
                .body(clienteService.actualizar(cliente));
    }
}

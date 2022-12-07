package acardenas.com.util;

import acardenas.com.model.Cliente;

public class DatosTest {

    public final static Cliente CLIENTE = Cliente.builder()
            .id(1)
            .codigoUnico("55s")
            .nombres("Armando")
            .apellidos("Cardenas")
            .tipoDocumento("DNI")
            .numeroDocumento("75221458").build();

    public final static Cliente CLIENTE_SIN_CODIGO_UNICO = Cliente.builder()
            .id(1)
            .nombres("Armando")
            .apellidos("Cardenas")
            .tipoDocumento("DNI")
            .numeroDocumento("75221458").build();

    public final static Cliente CLIENTE_PARA_ACTUALIZAR = Cliente.builder()
            .codigoUnico("55s")
            .nombres("Armando James")
            .numeroDocumento("75221458").build();

    public final static String MESSAGE_EXCEPTION_CLIENTE_EXISTENTE = "¡El cliente ya existe!";

    public final static String MESSAGE_EXCEPTION_CLIENTE_REQUERIDO = "¡No ha enviado el código del usuario!";

    public final static String MESSAGE_EXCEPTION_CLIENTE_NO_EXISTE = "¡No existe el Cliente con el código %s!";
}

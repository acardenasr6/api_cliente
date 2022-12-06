package acardenas.com.repository.impl;

import acardenas.com.model.Cliente;
import acardenas.com.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private JdbcTemplate jdbcTemplate;

    private final Logger logger = Logger.getLogger(ClienteRepositoryImpl.class.getName());

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Cliente> obtenerPorCodigo(String codigo) {

        String sql = "SELECT * FROM TB_CLIENTE WHERE codigo_unico=?";
        List<Cliente> clientes = jdbcTemplate.query(sql, new Object[] {codigo},
                (rs, rowNum) -> Cliente.builder()
                    .id(rs.getInt("id"))
                    .codigoUnico(rs.getString("codigo_unico"))
                    .nombres(rs.getString("nombres"))
                    .apellidos(rs.getString("apellidos"))
                    .tipoDocumento(rs.getString("tipo_documento"))
                    .numeroDocumento(rs.getString("numero_documento"))
                    .build());

        if (clientes.isEmpty()) {
            return Optional.empty();
        } else {
            return clientes.stream().findFirst();
        }
    }

    @Override
    public Optional<Integer> registrar(Cliente cliente) {

        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO TB_CLIENTE (codigo_unico, nombres, apellidos, tipo_documento, numero_documento) VALUES(?,?,?,?,?) ",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getCodigoUnico());
            stmt.setString(2, cliente.getNombres());
            stmt.setString(3, cliente.getApellidos());
            stmt.setString(4, cliente.getTipoDocumento());
            stmt.setString(5, cliente.getNumeroDocumento());
            int count = stmt.executeUpdate();
            if (count == 1) {
                try (ResultSet key = stmt.getGeneratedKeys()) {
                    if (key.next()) {
                        logger.info("Nuevo cliente: " + cliente.getNombres());
                        return Optional.of(key.getInt(1));
                    }
                }
            }
        } catch (SQLException error) {
            logger.log(Level.SEVERE, "Unexpected error", error);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> actualizar(Cliente cliente) {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement stmt = connection.prepareStatement("UPDATE TB_CLIENTE SET nombres = ?, " +
                    "apellidos = ?, tipo_documento = ?,numero_documento = ? WHERE codigo_unico=?");
            stmt.setString(1, cliente.getNombres());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getTipoDocumento());
            stmt.setString(4, cliente.getNumeroDocumento());
            stmt.setString(5, cliente.getCodigoUnico());
            return Optional.of(stmt.executeUpdate());
        } catch (SQLException error) {
            logger.log(Level.SEVERE, "Unexpected error", error);
        }
        return Optional.empty();
    }
}

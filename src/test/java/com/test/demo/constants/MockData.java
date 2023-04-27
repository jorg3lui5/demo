package com.test.demo.constants;

import com.test.demo.dto.ClienteEntityDTO;
import com.test.demo.entities.ClienteEntity;
import com.test.demo.entities.CuentaEntity;
import com.test.demo.entities.MovimientoEntity;
import com.test.demo.entities.PersonaEntity;
import com.test.demo.server.models.*;
import com.test.demo.server.models.Error;
import io.r2dbc.postgresql.client.Client;
import liquibase.pro.packaged.E;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MockData {

    private MockData() {
        throw new IllegalStateException("MockData class");
    }
    public static final String APP_NAME = "app-name";
    public static final String CALLER_NAME = "caller-name";
    public static final String PROCESS_CODE = "process-code";
    public static final String REQUEST_DATE = "request-date";
    public static final String REQUEST_ID = "request-id";
    public static final String X_SESSION = "x-session";
    public static final String X_DEVICE = "x-device";
    public static final String X_DEVICE_IP = "x-device-ip";
    public static final String X_LANGUAGE = "x-language";
    public static final String CUSTOMER_AFFILIATION_ID = "customer-affiliation-id";
    public static final String CUSTOMER_USER_ID = "customer-user-id";
    public static final String OPERATION_ID = "operationId";
    public static final String REFERENCE_ID = "referenceId";

    public static final String APP_NAME_VALUE = "BackOffice";
    public static final String CALLER_NAME_VALUE = "bbe-msa-bs-guided-experience";
    public static final String PROCESS_CODE_VALUE = "BJACAR01";
    public static final String REQUEST_DATE_VALUE = "2019-04-29 07:20:50.52Z";
    public static final String REQUEST_ID_VALUE = "123";
    public static final String X_SESSION_VALUE = "a9fd8deb-2aad-4d88-a7e3-d153c9e44b66";
    public static final String X_DEVICE_VALUE = "9939aadd00ee32";
    public static final String X_DEVICE_IP_VALUE = "localhost";
    public static final String X_LANGUAGE_VALUE = "EN";
    public static final String CUSTOMER_AFFILIATION_ID_VALUE = "qwerccf-a60b-45e8-b5e3-215924834e5";
    public static final String OPERATION_ID_VALUE = "operationId001";
    public static final String REFERENCE_ID_VALUE = "referenceId001";

    public static final String DIGITAL_FILE_SIGNATURE_ID = "1073D363-E6D9-4701-AA19-00E1B3DB5613";

    public static final String SIGNER_ID = "1073D363-E6D9-4701-AA19-00E1B3DB5612";

    public static final String NOMBRE_CLIENTE = "Juan Perez";
    public static final String NUMERO_CUENTA = "001234667";
    public static final TipoCuenta TIPO_CUENTA = TipoCuenta.AHORROS;
    public static final String TIPO_CUENTA_STRING = "AHORROS";
    public static final TipoMovimiento TIPO_MOVIMIENTO = TipoMovimiento.CREDITO;
    public static final String TIPO_MOVIMIENTO_STRING = "CREDITO";
    public static final Double SALDO = Double.valueOf(2000);
    public static final Boolean ESTADO = Boolean.TRUE;
    public static final Long ID = Long.valueOf(123456789);
    public static final LocalDate FECHA = LocalDate.of(2023,3,3);
    public static final String GENERO = "Masculino";
    public static final Integer EDAD = Integer.valueOf(20);
    public static final String DIRECCION = "Av. Americas";
    public static final String TELEFONO = "5553345564";
    public static final String CONTRASENIA = "1111";

    public static final String ERROR_CUENTA_NO_MODIFICADA_EXISTEN_MOVIMIENTOS = "No se puede modificar la cuenta porque ya existen movimientos realizados";



    public static Error getError() {
        return new Error()
                .errorCode("API-ERROR")
                .userMessage("user message")
                .developerMessage("developer message");
    }

    public static String getErrorString() {
        return "{" +
                "\"errorCode\":\"API-ERROR\"," +
                "\"userMessage\":\"user message\"," +
                "\"developerMessage\":\"developer message\"" +
                "}";
    }

    public static ReporteCuentasClienteResponse getReporteCuentasClienteResponse() {
        return new ReporteCuentasClienteResponse()
                .fecha(FECHA)
                .cliente(NOMBRE_CLIENTE)
                .numeroCuenta(NUMERO_CUENTA)
                .tipo(TIPO_CUENTA_STRING)
                .saldoInicial(SALDO)
                .estado(ESTADO)
                .movimiento("-5.00")
                .saldoDisponible(SALDO);
    }

    public static PostCuentaRequest getPostCuentaRequest() {
        return new PostCuentaRequest()
                .numero(NUMERO_CUENTA)
                .tipo(TIPO_CUENTA)
                .saldoInicial(SALDO)
                .clienteId(ID);
    }



    public static GetCuentaResponse getGetCuentaResponse() {
        return new GetCuentaResponse()
                .cuentaId(ID)
                .numero(NUMERO_CUENTA)
                .tipo(TIPO_CUENTA)
                .saldoInicial(SALDO)
                .estado(ESTADO)
                .cliente(getGetClienteResponse());
    }

    public static GetClienteResponse getGetClienteResponse() {
        return new GetClienteResponse()
                .clienteId(ID)
                .identificacion(NUMERO_CUENTA)
                .nombre(NOMBRE_CLIENTE)
                .genero(GENERO)
                .edad(EDAD)
                .direccion(DIRECCION)
                .telefono(TELEFONO)
                .estado(ESTADO);
    }

    public static PutCuentaRequest getPutCuentaRequest() {
        return new PutCuentaRequest()
                .cuentaId(ID)
                .numero(NUMERO_CUENTA)
                .tipo(TIPO_CUENTA)
                .saldoInicial(SALDO)
                .estado(ESTADO)
                .clienteId(ID);
    }

    public static PatchCuentaRequest getPatchCuentaRequest() {
        return new PatchCuentaRequest()
                .cuentaId(ID)
                .numero(NUMERO_CUENTA)
                .tipo(TIPO_CUENTA)
                .saldoInicial(SALDO)
                .estado(ESTADO);
    }

    public static CuentaEntity getCuentaEntity() {
        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setCuentaId(ID);
        cuenta.setNumero(NUMERO_CUENTA);
        cuenta.setTipo(TIPO_CUENTA_STRING);
        cuenta.setSaldoInicial(BigDecimal.valueOf(SALDO));
        cuenta.setEstado(ESTADO);
        cuenta.setClienteId(ID);
        return cuenta;
    }

    public static ClienteEntity getClienteEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setContrasenia(CONTRASENIA);
        clienteEntity.setEstado(ESTADO);
        return clienteEntity;
    }

    public static PersonaEntity getPersonaEntity() {
        PersonaEntity person = new PersonaEntity();
        person.setPersonaId(ID);
        person.setIdentificacion(NUMERO_CUENTA);
        person.setNombre(NOMBRE_CLIENTE);
        person.setGenero(GENERO);
        person.setEdad(EDAD);
        person.setDireccion(DIRECCION);
        person.setTelefono(TELEFONO);
        return person;
    }

    public static ClienteEntityDTO getClienteEntityDTO() {
        ClienteEntityDTO cliente = new ClienteEntityDTO();
        cliente.setClienteId(ID);
        cliente.setIdentificacion(NUMERO_CUENTA);
        cliente.setNombre(NOMBRE_CLIENTE);
        cliente.setGenero(GENERO);
        cliente.setEdad(EDAD);
        cliente.setDireccion(DIRECCION);
        cliente.setTelefono(TELEFONO);
        cliente.setContrasenia(CONTRASENIA);
        cliente.setEstado(ESTADO);
        return cliente;
    }

    public static MovimientoEntity getMovimientoEntity() {
        MovimientoEntity movimiento = new MovimientoEntity();
        movimiento.setMovimientoId(ID);
        movimiento.setFecha(FECHA);
        movimiento.setTipo(TIPO_MOVIMIENTO_STRING);
        movimiento.setValor(BigDecimal.valueOf(SALDO));
        movimiento.setSaldo(BigDecimal.valueOf(SALDO));
        movimiento.setCuentaId(ID);
        movimiento.setEstado(ESTADO);
        return movimiento;
    }


}

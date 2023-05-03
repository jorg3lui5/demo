package com.test.demo.error;

import org.springframework.http.HttpStatus;
import com.test.demo.server.models.Error;
import java.math.BigDecimal;


public class DemoError {

    private DemoError(){
        throw new IllegalStateException("This class cannot be initialized");
    }


    static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    static final HttpStatus NO_CONTENT = HttpStatus.NO_CONTENT;

    static final String USER_MESSAGE_01 = "Unexpected error";
    static final String DEVELOPER_MESSAGE_01 = "Unexpected error";

    public  static final FailureException FE01_UNEXPECTED_ERROR = new FailureException(
            new Error()
                    .errorCode("FE_01")
                    .status(BigDecimal.valueOf(INTERNAL_SERVER_ERROR.value()))
                    .userMessage(USER_MESSAGE_01)
                    .developerMessage(DEVELOPER_MESSAGE_01),
            INTERNAL_SERVER_ERROR.value(),
            INTERNAL_SERVER_ERROR);

    static final String USER_MESSAGE_02 = "Cliente no encontrado, verifique el Id";
    static final String DEVELOPER_MESSAGE_02 = "Registro no encontrado";

    public  static final FailureException FE02_CLIENTE_POR_ID_NO_ENCONTRADO = new FailureException(
            new Error()
                    .errorCode("FE_02")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_02)
                    .developerMessage(DEVELOPER_MESSAGE_02),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_03 = "No existen clientes registrados";
    static final String DEVELOPER_MESSAGE_03 = "No existen registros";

    public  static final FailureException FE03_ClIENTES_NO_ENCONTRADOS = new FailureException(
            new Error()
                    .errorCode("FE_03")
                    .status(BigDecimal.valueOf(NO_CONTENT.value()))
                    .userMessage(USER_MESSAGE_03)
                    .developerMessage(DEVELOPER_MESSAGE_03),
            NO_CONTENT.value(),
            NO_CONTENT);

    static final String USER_MESSAGE_04 = "No se encontró el cliente para asignar la cuenta";
    static final String DEVELOPER_MESSAGE_04 = "Registro no encontrado";

    public  static final FailureException FE04_NO_EXISTE_CLIENTE_PARA_CUENTA = new FailureException(
            new Error()
                    .errorCode("FE_04")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_04)
                    .developerMessage(DEVELOPER_MESSAGE_04),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_05 = "Cuenta no encontrada, verifique el Id";
    static final String DEVELOPER_MESSAGE_05 = "Registro no encontrado";

    public  static final FailureException FE05_CUENTA_POR_ID_NO_ENCONTRADA = new FailureException(
            new Error()
                    .errorCode("FE_05")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_05)
                    .developerMessage(DEVELOPER_MESSAGE_05),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_06 = "Movimiento no encontrado, verifique el Id";
    static final String DEVELOPER_MESSAGE_06 = "Registro no encontrado";

    public  static final FailureException FE06_MOVIMIENTO_POR_ID_NO_ENCONTRADO = new FailureException(
            new Error()
                    .errorCode("FE_06")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_06)
                    .developerMessage(DEVELOPER_MESSAGE_06),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_07 = "No se puede modificar la cuenta porque ya existen movimientos realizados";
    static final String DEVELOPER_MESSAGE_07 = "Registro no modificado";

    public  static final FailureException FE07_CUENTA_NO_MODIFICADA_EXISTEN_MOVIMIENTOS = new FailureException(
            new Error()
                    .errorCode("FE_07")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_07)
                    .developerMessage(DEVELOPER_MESSAGE_07),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_08 = "No se encontró la cuenta a realizar el movimiento";
    static final String DEVELOPER_MESSAGE_08 = "Registro no encontrado";

    public  static final FailureException FE08_NO_EXISTE_CUENTA_PARA_MOVIMIENTO = new FailureException(
            new Error()
                    .errorCode("FE_08")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_08)
                    .developerMessage(DEVELOPER_MESSAGE_08),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_09 = "La fecha del movimiento debe ser mayor a la fecha del último movimiento";
    static final String DEVELOPER_MESSAGE_09 = "Movimiento no realizado";

    public  static final FailureException FE09_FECHA_INVALIDA_MOVIMIENTO = new FailureException(
            new Error()
                    .errorCode("FE_09")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_09)
                    .developerMessage(DEVELOPER_MESSAGE_09),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_10 = "No valor que se quiere debitar es mayor al saldo actual";
    static final String DEVELOPER_MESSAGE_10 = "Movimiento no realizado";

    public  static final FailureException FE10_VALOR_DEBITO_INVALIDO = new FailureException(
            new Error()
                    .errorCode("FE_10")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_10)
                    .developerMessage(DEVELOPER_MESSAGE_10),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_11 = "El cliente no tiene cuentas registradas";
    static final String DEVELOPER_MESSAGE_11 = "Sin registros";

    public  static final FailureException FE11_CLIENTE_SIN_CUENTAS = new FailureException(
            new Error()
                    .errorCode("FE_11")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_11)
                    .developerMessage(DEVELOPER_MESSAGE_11),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_12 = "Error al eliminar el registro, verifique el Id";
    static final String DEVELOPER_MESSAGE_12 = "Registro no eliminado";

    public  static final FailureException FE12_ELIMINACION_ERROR = new FailureException(
            new Error()
                    .errorCode("FE_12")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_12)
                    .developerMessage(DEVELOPER_MESSAGE_12),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_13 = "Error al actualizar el registro, verifique los datos";
    static final String DEVELOPER_MESSAGE_13 = "Registro no actualizado";

    public  static final FailureException FE13_ACTUALIZACION_ERROR = new FailureException(
            new Error()
                    .errorCode("FE_13")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_13)
                    .developerMessage(DEVELOPER_MESSAGE_13),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_14 = "Error al recuperar Los registros";
    static final String DEVELOPER_MESSAGE_14 = "Registros no recuperados";

    public  static final FailureException FE14_RECUPERAR_TODOS_ERROR = new FailureException(
            new Error()
                    .errorCode("FE_14")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_14)
                    .developerMessage(DEVELOPER_MESSAGE_14),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_15 = "Error al recuperar el registro, verifique el Id";
    static final String DEVELOPER_MESSAGE_15 = "Registro no recuperado";

    public  static final FailureException FE15_RECUPERAR_ERROR = new FailureException(
            new Error()
                    .errorCode("FE_15")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_15)
                    .developerMessage(DEVELOPER_MESSAGE_15),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_16 = "Error al guardar el registro, verifique los datos";
    static final String DEVELOPER_MESSAGE_16 = "Registro no guardado";

    public  static final FailureException FE16_GUARDAR_ERROR = new FailureException(
            new Error()
                    .errorCode("FE_16")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_16)
                    .developerMessage(DEVELOPER_MESSAGE_16),
            BAD_REQUEST.value(),
            BAD_REQUEST);

    static final String USER_MESSAGE_17 = "Método aún no implementado, lo implementaremos pronto";
    static final String DEVELOPER_MESSAGE_17 = "Método no implementado";

    public  static final FailureException FE17_METODO_NO_IMPLEMENTADO = new FailureException(
            new Error()
                    .errorCode("FE_17")
                    .status(BigDecimal.valueOf(BAD_REQUEST.value()))
                    .userMessage(USER_MESSAGE_17)
                    .developerMessage(DEVELOPER_MESSAGE_17),
            BAD_REQUEST.value(),
            BAD_REQUEST);

}

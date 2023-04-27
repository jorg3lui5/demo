package com.test.demo.service;

import com.test.demo.constants.MockData;
import com.test.demo.error.DemoError;
import com.test.demo.error.FailureException;
import com.test.demo.repository.ClientesRepository;
import com.test.demo.repository.CuentasRepository;
import com.test.demo.repository.MovimientosRepository;
import com.test.demo.service.impl.CuentasServiceImpl;
import com.test.demo.util.mapper.CuentasMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.test.demo.constants.MockData.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CuentasServiceImpl.class,
        CuentasMapperImpl.class})
class CuentasServiceTest {

    @MockBean
    CuentasRepository cuentasRepository;

    @MockBean
    ClientesRepository clientesRepository;

    @MockBean
    MovimientosRepository movimientosRepository;

    @MockBean
    ClientesService clientesService;

    @Autowired
    CuentasService cuentasService;

    @Test
    void getCuentaSuccess() {
        when(cuentasRepository.findByCuentaId(anyLong()))
                .thenReturn(Mono.just(MockData.getCuentaEntity()));
        when(clientesService.getCliente(anyLong()))
                .thenReturn(Mono.just(MockData.getGetClienteResponse()));
        StepVerifier
                .create(cuentasService.getCuenta(ID))
                .expectNextMatches(response -> response.getNumero().equals(MockData.getGetCuentaResponse().getNumero()))
                .verifyComplete();
    }

    @Test
    void getCuentasSuccess() {
        when(cuentasRepository.findAll())
                .thenReturn(Flux.just(MockData.getCuentaEntity()));
        when(clientesService.getCliente(anyLong()))
                .thenReturn(Mono.just(MockData.getGetClienteResponse()));
        StepVerifier
                .create(cuentasService.getCuentas())
                .expectNextMatches(getCuentaResponse ->getCuentaResponse.getCuentaId().equals(ID))
                .verifyComplete();
    }

    @Test
    void postCuentaSuccess() {
        when(clientesRepository.findById(anyLong()))
                .thenReturn(Mono.just(MockData.getClienteEntity()));
        when(cuentasRepository.save(any()))
                .thenReturn(Mono.just(MockData.getCuentaEntity()));
        StepVerifier
                .create(cuentasService.postCuenta(Mono.just(MockData.getPostCuentaRequest())))
                .verifyComplete();
    }

    @Test
    void putCuentaSuccess() {
        when(cuentasRepository.findByCuentaId(anyLong()))
                .thenReturn(Mono.just(MockData.getCuentaEntity()));
        when(movimientosRepository.findByCuentaId(anyLong()))
                .thenReturn(Flux.empty());
        when(clientesRepository.findById(anyLong()))
                .thenReturn(Mono.just(MockData.getClienteEntity()));
        when(cuentasRepository.save(any()))
                .thenReturn(Mono.just(MockData.getCuentaEntity()));
        StepVerifier
                .create(cuentasService.putCuenta(Mono.just(MockData.getPutCuentaRequest())))
                .verifyComplete();
    }

    @Test
    void putCuentaYaTieneMovimientosError() {
        when(cuentasRepository.findByCuentaId(anyLong()))
                .thenReturn(Mono.just(MockData.getCuentaEntity()));
        when(movimientosRepository.findByCuentaId(anyLong()))
                .thenReturn(Flux.just(MockData.getMovimientoEntity()));
        StepVerifier
                .create(cuentasService.putCuenta(Mono.just(MockData.getPutCuentaRequest())))
                .verifyErrorMatches(throwable -> throwable.equals(DemoError.FE07_CUENTA_NO_MODIFICADA_EXISTEN_MOVIMIENTOS));
    }

    @Test
    void patchCuentaSuccess() {
        when(cuentasRepository.findByCuentaId(anyLong()))
                .thenReturn(Mono.just(MockData.getCuentaEntity()));
        when(movimientosRepository.findByCuentaId(anyLong()))
                .thenReturn(Flux.empty());
        when(cuentasRepository.save(any()))
                .thenReturn(Mono.just(MockData.getCuentaEntity()));
        StepVerifier
                .create(cuentasService.patchCuenta(Mono.just(MockData.getPatchCuentaRequest())))
                .verifyComplete();
    }

    @Test
    void patchCuentaYaTieneMovimientosError() {
        when(cuentasRepository.findByCuentaId(anyLong()))
                .thenReturn(Mono.just(MockData.getCuentaEntity()));
        when(movimientosRepository.findByCuentaId(anyLong()))
                .thenReturn(Flux.just(MockData.getMovimientoEntity()));
        StepVerifier
                .create(cuentasService.patchCuenta(Mono.just(MockData.getPatchCuentaRequest())))
                .verifyErrorMatches(throwable -> throwable.equals(DemoError.FE07_CUENTA_NO_MODIFICADA_EXISTEN_MOVIMIENTOS));
    }

    @Test
    void getReporteCuentasClienteSuccess() {
        when(clientesRepository.findByClienteId(anyLong()))
                .thenReturn(Mono.just(MockData.getClienteEntityDTO()));
        when(cuentasRepository.findByClienteId(anyLong()))
                .thenReturn(Flux.just(MockData.getCuentaEntity()));
        when(movimientosRepository.findMovimientosByCuentaId(anyLong()))
                .thenReturn(Flux.just(MockData.getMovimientoEntity()));
        StepVerifier
                .create(cuentasService.getReporteCuentasCliente(ID,FECHA,FECHA))
                .expectNextMatches(response ->response.getNumeroCuenta().equals(NUMERO_CUENTA))
                .verifyComplete();
    }
}

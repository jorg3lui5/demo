package com.test.demo.delegate;


import com.test.demo.api.delegate.CuentasApiDelegateImpl;
import com.test.demo.constants.MockData;
import com.test.demo.server.models.PatchCuentaRequest;
import com.test.demo.server.models.PostCuentaRequest;
import com.test.demo.server.models.PutCuentaRequest;
import com.test.demo.service.CuentasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CuentasApiDelegateImpl.class)
class CuentasApiTets {

    @MockBean
    CuentasService cuentasService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void getReporteCuentasClienteSuccess() {
        when(cuentasService.getReporteCuentasCliente(anyLong(), any(), any()))
                .thenReturn(Flux.just(MockData.getReporteCuentasClienteResponse()));

        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/v1/test/cuentas/reporte/1")
                                .queryParam("fechaInicio", LocalDate.of(2023,3,3))
                                .queryParam("fechaFin", LocalDate.of(2023,5,16))
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class);
    }

    @Test
    void getCuentaSuccess() {
        when(cuentasService.getCuenta(anyLong()))
                .thenReturn(Mono.just(MockData.getGetCuentaResponse()));

        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/v1/test/cuentas/1")
                                .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getCuentasSuccess() {
        when(cuentasService.getCuentas())
                .thenReturn(Flux.just(MockData.getGetCuentaResponse()));

        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/v1/test/cuentas")
                                .build())
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void postCuentaSuccess() {
        when(cuentasService.postCuenta(any()))
                .thenReturn(Mono.empty().then());

        webTestClient.post()
                .uri("/v1/test/cuentas")
                .body(Mono.just(MockData.getPostCuentaRequest()), PostCuentaRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void putCuentaSuccess() {
        when(cuentasService.putCuenta(any()))
                .thenReturn(Mono.empty().then());

        webTestClient.put()
                .uri("/v1/test/cuentas")
                .body(Mono.just(MockData.getPutCuentaRequest()), PutCuentaRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void patchCuentaSuccess() {
        when(cuentasService.patchCuenta(any()))
                .thenReturn(Mono.empty().then());

        webTestClient.patch()
                .uri("/v1/test/cuentas")
                .body(Mono.just(MockData.getPatchCuentaRequest()), PatchCuentaRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

}

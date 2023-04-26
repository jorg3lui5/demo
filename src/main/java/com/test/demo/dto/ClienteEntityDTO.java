package com.test.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntityDTO {

    private Long clienteId;

    private String identificacion;

    private String nombre;

    private String genero;

    private Integer edad;

    private String direccion;

    private String telefono;

    private String contrasenia;

    private Boolean estado;
}

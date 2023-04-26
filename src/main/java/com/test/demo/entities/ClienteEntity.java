package com.test.demo.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cliente")
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    @Id
    @Column("cliente_id")
    private Long clienteId;

    @Column("contrasenia")
    private String contrasenia;

    @Column("estado")
    private Boolean estado;

    @Column("persona_id")
    private Long personaId;
}

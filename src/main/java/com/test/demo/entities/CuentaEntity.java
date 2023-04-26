package com.test.demo.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Table("cuenta")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEntity {

  @Id
  @Column("cuenta_id")
  private Long cuentaId;

  @Column("numero")
  private String numero;

  @Column("tipo")
  private String tipo;

  @Column("saldo_inicial")
  private BigDecimal saldoInicial;

  @Column("estado")
  private Boolean estado;

  @Column("cliente_id")
  private Long clienteId;
}



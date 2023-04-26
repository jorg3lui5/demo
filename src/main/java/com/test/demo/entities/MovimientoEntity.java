package com.test.demo.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table("movimiento")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoEntity {

  @Id
  @Column("movimiento_id")
  private Long movimientoId;

  @Column("fecha")
  private LocalDate fecha;

  @Column("tipo")
  private String tipo;

  @Column("valor")
  private BigDecimal valor;

  @Column("saldo")
  private BigDecimal saldo;

  @Column("estado")
  private Boolean estado;

  @Column("cuenta_id")
  private Long cuentaId;
}

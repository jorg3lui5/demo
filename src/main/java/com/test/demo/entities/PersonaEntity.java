package com.test.demo.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Table("persona")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaEntity {

  @Id
  @Column("persona_id")
  private Long personaId;

  @Column("identificacion")
  private String identificacion;

  @Column("nombre")
  private String nombre;

  @Column("genero")
  private String genero;

  @Column("edad")
  private Integer edad;

  @Column("direccion")
  private String direccion;

  @Column("telefono")
  private String telefono;

}

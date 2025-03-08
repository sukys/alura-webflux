package com.lsukys.codechella.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lsukys.codechella.enums.TipoIngresso;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Table("ingressos")
public class Ingresso {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "envento_id")
    @JsonBackReference
    private Evento evento;

    @Builder.Default
    @Column("numero_controle")
    private String numeroDeControle = UUID.randomUUID().toString();

    @Column("data_venda")
    private LocalDate dataVenda;

    @Column("tipo")
    private TipoIngresso tipo;

    @Column("valor")
    private Double valor;

    @Column("total")
    private int total;
}

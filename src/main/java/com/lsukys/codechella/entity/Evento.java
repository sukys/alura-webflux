package com.lsukys.codechella.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lsukys.codechella.enums.TipoEvento;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Table("eventos")
public class Evento {

    @Id
    @GeneratedValue
    private Long id;

    private TipoEvento tipo;

    private String nome;

    private LocalDate data;

    private String descricao;

    @Column("qtd_ingressos")
    private Integer quantidadeIngressos;

//    @Builder.Default
//    @JsonManagedReference
//    @OneToMany(mappedBy = "evento")
//    private List<Ingresso> ingressos = new ArrayList<>();


//    public Integer getQuantidadeIngressosDisponiveis() {
//        return quantidadeIngressos - ingressos.size();
//    }

}

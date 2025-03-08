package com.lsukys.codechella.entity.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lsukys.codechella.entity.Evento;
import com.lsukys.codechella.entity.Ingresso;
import com.lsukys.codechella.enums.TipoEvento;
import com.lsukys.codechella.enums.TipoIngresso;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record IngressoDto(
        Long id,
        EventoDto evento,
        String numeroDeControle,
        LocalDate dataVenda,
        TipoIngresso tipo,
        Double valor,
        int total
) {

    public static IngressoDto toDto(Ingresso ingresso) {
        return IngressoDto.builder()
                .id(ingresso.getId())
                .evento(EventoDto.toDto(ingresso.getEvento()))
                .numeroDeControle(ingresso.getNumeroDeControle())
                .dataVenda(ingresso.getDataVenda())
                .tipo(ingresso.getTipo())
                .valor(ingresso.getValor())
                .total(ingresso.getTotal())
                .build();
    }

    public Ingresso toEntity() {
        return Ingresso.builder()
                .id(this.id)
                .evento(this.evento.toEntity())
                .numeroDeControle(this.numeroDeControle)
                .dataVenda(this.dataVenda)
                .tipo(this.tipo)
                .valor(this.valor)
                .total(this.total)
                .build();
    }

}

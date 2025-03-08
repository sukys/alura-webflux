package com.lsukys.codechella.entity.dtos;

import com.lsukys.codechella.entity.Evento;
import com.lsukys.codechella.enums.TipoEvento;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;

@Builder
public record EventoDto(
     Long id,
     TipoEvento tipo,
     String nome,
     LocalDate data,
     String descricao,
     Integer quantidadeIngressos
) {

    public static EventoDto toDto(Evento evento) {
        return EventoDto.builder()
                .id(evento.getId())
                .tipo(evento.getTipo())
                .nome(evento.getNome())
                .data(evento.getData())
                .descricao(evento.getDescricao())
                .quantidadeIngressos(evento.getQuantidadeIngressos())
                .build();
    }

    public Evento toEntity() {
        return Evento.builder()
                .id(this.id)
                .tipo(this.tipo)
                .nome(this.nome)
                .data(this.data)
                .descricao(this.descricao)
                .quantidadeIngressos(this.quantidadeIngressos)
                .build();
    }

}

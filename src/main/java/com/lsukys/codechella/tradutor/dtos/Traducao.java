package com.lsukys.codechella.tradutor.dtos;

import com.lsukys.codechella.entity.Evento;

import java.util.List;

public record Traducao(List<Texto> translations) {

    public String getTexto() {
        return translations.getFirst().text();
    }

    public Texto[] getTextos() {
        return translations.toArray(Texto[]::new);
    }


}

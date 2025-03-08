package com.lsukys.codechella.controller;

import com.lsukys.codechella.tradutor.service.TraducaoDeTextos;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tradutor")
public class TradutorController {

    @GetMapping(value = "/verificarUso" )
    public Mono<String> obterUso() {
        return TraducaoDeTextos.verificarUso();
    }

}

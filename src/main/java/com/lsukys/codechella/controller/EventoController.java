package com.lsukys.codechella.controller;

import com.lsukys.codechella.entity.dtos.EventoDto;
import com.lsukys.codechella.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    private final Sinks.Many<EventoDto> eventosSink;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
        this.eventosSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping (produces = MediaType.TEXT_EVENT_STREAM_VALUE )
    public Flux<EventoDto> obterTodos() {
        return Flux.merge(eventoService.obterTodos(), eventosSink.asFlux()).delayElements(Duration.ofMillis(500));
    }

    @GetMapping(value = "/categoria/{tipo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterPorTipo(@PathVariable String tipo) {
        return Flux.merge(eventoService.obterPorTipo(tipo), eventosSink.asFlux()).delayElements(Duration.ofSeconds(4));
    }

    @GetMapping("/{id}")
    public Mono<EventoDto> obterPorId(@PathVariable Long id) {
        return eventoService.obterPorId(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EventoDto> criarEvento(@RequestBody EventoDto eventoDto) {
        return eventoService.cadastrar(eventoDto).doOnSuccess(eventosSink::tryEmitNext);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePorId(@PathVariable Long id) {
        return eventoService.excluir(id);
    }

    @PutMapping("/{id}")
    public Mono<EventoDto> alterarEvento(@PathVariable Long id, @RequestBody EventoDto eventoDto) {
        return eventoService.alterar(id, eventoDto);
    }


    @GetMapping("/{id}/traduzir-descricao/{idioma}")
    public Mono<String> traduzirDescricaoPorId(@PathVariable Long id, @PathVariable String idioma) {
        return eventoService.obterTraducaoDaDescricao(id, idioma);
    }

    @GetMapping("/{id}/traduzir/{idioma}")
    public Mono<EventoDto> traduzirEventoPorId(@PathVariable Long id, @PathVariable String idioma) {
        return eventoService.obterTraducaoDoEvento(id, idioma);
    }
}

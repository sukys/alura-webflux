package com.lsukys.codechella.service;

import com.lsukys.codechella.entity.dtos.EventoDto;
import com.lsukys.codechella.enums.TipoEvento;
import com.lsukys.codechella.repository.EventoRepository;
import com.lsukys.codechella.tradutor.service.TraducaoDeTextos;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class EventoService {

    private EventoRepository eventoRepository;

    public Flux<EventoDto> obterTodos() {
        return eventoRepository.findAll().map(EventoDto::toDto);
    }

    public Mono<EventoDto> obterPorId(Long id) {
        return eventoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND)))
                .map(EventoDto::toDto);
    }

    public Mono<EventoDto> cadastrar(EventoDto eventoDto) {
        return eventoRepository.save(eventoDto.toEntity()).map(EventoDto::toDto);
    }


    public Mono<Void> excluir(Long id) {
        return eventoRepository.findById(id).flatMap(eventoRepository::delete);
    }

    public Mono<EventoDto> alterar(Long id, EventoDto eventoDto) {
        return eventoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                .flatMap(eventoExistente -> {
                    eventoExistente.setTipo(eventoDto.tipo());
                    eventoExistente.setNome(eventoDto.nome());
                    eventoExistente.setData(eventoDto.data());
                    eventoExistente.setDescricao(eventoDto.descricao());
                    eventoExistente.setQuantidadeIngressos(eventoDto.quantidadeIngressos());
                    return eventoRepository.save(eventoExistente);
                })
                .map(EventoDto::toDto);
    }

    public Flux<EventoDto> obterPorTipo(String tipo) {
        TipoEvento tipoEvento = TipoEvento.valueOf(tipo.toUpperCase());
        return eventoRepository.findByTipo(tipoEvento).map(EventoDto::toDto);
    }

    public Mono<String> obterTraducaoDaDescricao(Long id, String idioma) {
        return eventoRepository.findById(id)
                .flatMap(e -> TraducaoDeTextos.obterTraducao(e.getDescricao(), idioma));
    }

    public Mono<EventoDto> obterTraducaoDoEvento(Long id, String idioma) {
        return eventoRepository.findById(id)
                .flatMap(e -> TraducaoDeTextos.obterTraducaoDoEvento(idioma, e))
                .map(EventoDto::toDto);
    }
}

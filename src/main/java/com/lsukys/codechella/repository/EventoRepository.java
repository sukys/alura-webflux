package com.lsukys.codechella.repository;


import com.lsukys.codechella.entity.Evento;
import com.lsukys.codechella.enums.TipoEvento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {

    Flux<Evento> findByTipo(TipoEvento tipoEvento);

}

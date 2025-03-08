package com.lsukys.codechella.repository;


import com.lsukys.codechella.entity.Ingresso;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
public interface IngressoRepository extends ReactiveCrudRepository<Ingresso, Long> {


    Flux<Ingresso> findByIdOrNumeroDeControle(Long id, String numeroDeControle);

    Flux<Ingresso> findAllByEventoId(Long enventoId);
}

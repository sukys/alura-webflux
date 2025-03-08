package com.lsukys.codechella.repository;

import com.lsukys.codechella.entity.Venda;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VendaRepository extends ReactiveCrudRepository<Venda, Long> {
}
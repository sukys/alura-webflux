package com.lsukys.codechella.service;


import com.lsukys.codechella.entity.Venda;
import com.lsukys.codechella.entity.dtos.CompraDto;
import com.lsukys.codechella.entity.dtos.IngressoDto;
import com.lsukys.codechella.repository.IngressoRepository;
import com.lsukys.codechella.repository.VendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class IngressoService {

    private IngressoRepository repositorio;
    private VendaRepository vendaRepository;

    public Flux<IngressoDto> obterTodos() {
        return  repositorio.findAll()
                .map(IngressoDto::toDto);
    }

    public Mono<IngressoDto> obterPorId(Long id) {
        return  repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(IngressoDto::toDto);
    }

    public Mono<IngressoDto> cadastrar(IngressoDto dto) {
        return repositorio.save(dto.toEntity())
                .map(IngressoDto::toDto);
    }

    public Mono<Void> excluir(Long id) {
        return repositorio.findById(id)
                .flatMap(repositorio::delete);
    }

    public Mono<IngressoDto> alterar(Long id, IngressoDto dto) {
        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                .flatMap(ingresso -> {
                    ingresso.setEvento(dto.toEntity().getEvento());
                    ingresso.setTipo(dto.tipo());
                    ingresso.setValor(dto.valor());
                    ingresso.setTotal(dto.total());
                    return repositorio.save(ingresso);
                })
                .map(IngressoDto::toDto);
    }

    @Transactional
    public Mono<IngressoDto> comprar(CompraDto dto) {
        return repositorio.findById(dto.ingressoId())
                .flatMap(ingresso -> {
                    Venda venda = new Venda();
                    venda.setIngressoId(ingresso.getId());
                    venda.setTotal(dto.total());
                    return vendaRepository.save(venda).then(Mono.defer(() -> {
                        ingresso.setTotal(ingresso.getTotal() - dto.total());
                        return repositorio.save(ingresso);
                    }));
                }).map(IngressoDto::toDto);
    }
}

//
// @Service
//@AllArgsConstructor
//public class IngressoService {
//
//    IngressoRepository ingressoRepository;
//
//    EventoService eventoService;
//
//    public Flux<Ingresso> findByIdOuNumeroDeCotrole(Long id, String numeroDeCotrole) {
//        return ingressoRepository.findByIdOrNumeroDeControle(id, numeroDeCotrole);
//    }
//
//    public List<IngressoDto> criarIngressos(Long eventoId, Integer quantidadeDeIngressos) throws ExecutionException, InterruptedException {
//            Evento evento = eventoService.obterPorId(eventoId)
//                    .toFuture().get().toEntity();
//
//            if (quantidadeDeIngressos > evento.getQuantidadeIngressosDisponiveis() ) {
//                throw new ResponseStatusException(NOT_ACCEPTABLE, "Quantidade de ingressos indisponivel." );
//            }
//
//            List<IngressoDto> ingressos = new ArrayList<>();
//            for (int i = 0; i < quantidadeDeIngressos; i++) {
//                Ingresso ingresso = Ingresso.builder()
//                        .dataVenda(LocalDate.now())
//                        .evento(evento)
//                        .build();
//                ingresso = ingressoRepository.save(ingresso).toFuture().get();
//                ingressos.add(IngressoDto.toDto(ingresso));
//                evento.getIngressos().add(ingresso);
//            }
//            return ingressos;
//    }
//
//    public void cancelarIngressos(Long eventoId, long ingressoId) throws ExecutionException, InterruptedException {
//        Evento evento = eventoService.obterPorId(eventoId)
//                .toFuture().get().toEntity();
//
//        ingressoRepository.deleteById(ingressoId).switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND)));
//
//    }
//
//
//    public Flux<IngressoDto> obterTodosPorEvento(Long enventoId) {
//        return ingressoRepository.findAllByEventoId(enventoId).map(IngressoDto::toDto);
//    }
//}

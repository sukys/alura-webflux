package com.lsukys.codechella;

import com.lsukys.codechella.entity.dtos.EventoDto;
import com.lsukys.codechella.enums.TipoEvento;
import com.lsukys.codechella.service.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient(timeout = "10000")
class CodechellaApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EventoService eventoService;


    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000L))
                .build();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void cadastraNovoEvento() {
        EventoDto dto = EventoDto.builder()
                .id(null)
                .tipo(TipoEvento.SHOW)
                .nome("kiss")
                .data(LocalDate.parse("2025-01-01"))
                .descricao("Show exclusivo da melhor banda que existe")
                .quantidadeIngressos(50)
                .build();

        webTestClient.post().uri("/eventos").bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EventoDto.class)
                .value(response -> {
                    assertNotNull(response.id());
                    assertEquals(dto.tipo(), response.tipo());
                    assertEquals(dto.nome(), response.nome());
                    assertEquals(dto.data(), response.data());
                    assertEquals(dto.descricao(), response.descricao());
                    assertEquals(dto.quantidadeIngressos(), response.quantidadeIngressos());
                });
    }



    @Test
    void buscarEventoPorId() {
        EventoDto dto = EventoDto.builder()
                .id(13L)
                .tipo(TipoEvento.SHOW)
                .nome("The Weeknd")
                .data(LocalDate.parse("2025-11-02"))
                .descricao("Um show eletrizante ao ar livre com muitos efeitos especiais.")
                .build();

        webTestClient.get().uri("/eventos/13")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(EventoDto.class)
                .value(response -> {
                    assertEquals(dto.id(), response.id());
                    assertEquals(dto.tipo(), response.tipo());
                    assertEquals(dto.nome(), response.nome());
                    assertEquals(dto.data(), response.data());
                    assertEquals(dto.descricao(), response.descricao());
                });
    }



    //@Test
    @Disabled
    void buscarEventos() {
        EventoDto dto = EventoDto.builder()
                .id(13L)
                .tipo(TipoEvento.SHOW)
                .nome("The Weeknd")
                .data(LocalDate.parse("2025-11-02"))
                .descricao("Um show eletrizante ao ar livre com muitos efeitos especiais.")
                .build();

        webTestClient.get().uri("/eventos")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(EventoDto.class)
                .value(response -> {
                    assertEquals(14, response.size());

                    EventoDto eventoDto = response.get(12);

                    assertEquals(dto.id(), eventoDto.id());
                    assertEquals(dto.tipo(), eventoDto.tipo());
                    assertEquals(dto.nome(), eventoDto.nome());
                    assertEquals(dto.data(), eventoDto.data());
                    assertEquals(dto.descricao(), eventoDto.descricao());

                });
    }


    //@Test
    @Disabled
    void testServiceMethod() {
        Flux<EventoDto> flux = eventoService.obterTodos();

        EventoDto dto = EventoDto.builder()
                .id(13L)
                .tipo(TipoEvento.SHOW)
                .nome("The Weeknd")
                .data(LocalDate.parse("2025-11-02"))
                .descricao("Um show eletrizante ao ar livre com muitos efeitos especiais.")
                .build();

        StepVerifier.create(flux)
                .expectNextCount(13)
                .expectComplete()
                .verify(Duration.ofSeconds(10));
//                .verifyThenAssertThat( eventoDto -> {
//                    evento
//                    () -> {dto.id(), eventoDto. }
//                    assertEquals();
//                    assertEquals(dto.tipo(), eventoDto.tipo());
//                    assertEquals(dto.nome(), eventoDto.nome());
//                    assertEquals(dto.data(), eventoDto.data());
//                    assertEquals(dto.descricao(), eventoDto.descricao());
//
//                });
    }

}

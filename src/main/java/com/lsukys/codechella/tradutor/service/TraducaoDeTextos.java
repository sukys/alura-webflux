package com.lsukys.codechella.tradutor.service;


import com.lsukys.codechella.entity.Evento;
import com.lsukys.codechella.tradutor.config.DeepLConfig;
import com.lsukys.codechella.tradutor.dtos.Texto;
import com.lsukys.codechella.tradutor.dtos.Traducao;
import com.lsukys.codechella.tradutor.dtos.UsoDeepl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.lsukys.codechella.tradutor.config.DeepLConstantes.GET_USAGE_URL;
import static com.lsukys.codechella.tradutor.config.DeepLConstantes.TRANSLATE_URL;

@Slf4j
@Service
public class TraducaoDeTextos {


    public static Mono<String> obterTraducao(String text, String idioma) {
        WebClient webClient = WebClient.builder().baseUrl(TRANSLATE_URL).build();

        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();

        req.add("text", text);
        req.add("target_lang", idioma);

        return webClient.post()
                .header("Authorization", DeepLConfig.getDeeplApiKey())
                .body(BodyInserters.fromFormData(req))
                .retrieve()
                .bodyToMono(Traducao.class)
                .map(Traducao::getTexto);
    }

    public static Mono<Evento> obterTraducaoDoEvento(String idioma, Evento evento) {
        WebClient webClient = WebClient.builder().baseUrl(TRANSLATE_URL).build();

        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();

        req.add("text", evento.getNome());
        req.add("text", evento.getDescricao());
        req.add("target_lang", idioma);

         return webClient.post()
                .header("Authorization", DeepLConfig.getDeeplApiKey())
                .body(BodyInserters.fromFormData(req))
                .retrieve()
                .bodyToMono(Traducao.class)
                .map(Traducao::getTextos)
                 .flatMap(texto -> {
                    evento.setNome(texto[0].text());
                    evento.setDescricao(texto[1].text());
                    return Mono.just(evento);
                });
    }






    public static Mono<String> verificarUso() {
        WebClient webClient = WebClient.builder().baseUrl(GET_USAGE_URL).build();

        return webClient.get()
                .header("Authorization", DeepLConfig.getDeeplApiKey())
                .retrieve()
                .bodyToMono(UsoDeepl.class)
                .map(UsoDeepl::getUsage);
    }


}

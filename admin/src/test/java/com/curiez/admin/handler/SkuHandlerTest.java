package com.curiez.admin.handler;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.dto.SkuDTO;
import com.curiez.admin.model.Product;
import com.curiez.admin.model.Sku;
import com.curiez.admin.repository.SkuRepository;
import com.curiez.admin.route.SkuRouter;
import com.curiez.admin.service.SkuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@Tag("INT")
@Tag("Handler")
@ContextConfiguration(classes = {SkuRouter.class,SkuHandler.class, SkuService.class})
@WebFluxTest
class SkuHandlerTest {

    @MockBean
    private SkuRepository repository;
    @Autowired
    private WebTestClient webClient;
    Sku s;
    SkuDTO sDTO;

    @BeforeEach
    void setUp() {
         s = Sku.builder().id("S1").name("Name").description("Description").build();
         sDTO = SkuDTO.builder().id("S1").name("Name").description("Description").build();
    }

    @Test
    @DisplayName("INT: Create or Save Sku")
    void saveSku() {
        BDDMockito.given(repository.save(s))
                .willReturn(Mono.just(s));

        webClient.put()
                .uri("/sku")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(sDTO))
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }



    @Test
    @DisplayName("INT: Get SKu")
    void getSku() {
        BDDMockito.given(repository.findById("S1")).willReturn(Mono.just(s));

        webClient.get()
                .uri("/sku/S1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isOk()
                .expectBody(SkuDTO.class)
                .isEqualTo(sDTO);

        BDDMockito.verify(repository,BDDMockito.atMost(1)).findById("P1");
    }

    @Test
    @DisplayName("INT: Get Sku when not exists")
    void getSku_when_not_exists() {
        BDDMockito.given(repository.findById("S1")).willReturn(Mono.empty());

        webClient.get()
                .uri("/sku/S1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isNoContent();

        BDDMockito.verify(repository,BDDMockito.atMost(1)).findById("P1");
    }


    @Test
    @DisplayName("INT: Update Sku ignoring the empty fields")
    void updateSku() {

        Sku skuUpdated = Sku.builder().id("S1").name("New_Name").description("Description").build();
        SkuDTO changeSkuDTO = SkuDTO.builder().id("S1").name("New_Name").build();
        SkuDTO updatedSkuDTO = SkuDTO.builder().id("S1").name("New_Name").description("Description").build();

        BDDMockito.given(repository.save(skuUpdated))
                .willReturn(Mono.just(skuUpdated));

        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.just(s));

        webClient.patch()
                .uri("/sku")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(changeSkuDTO))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SkuDTO.class)
                .isEqualTo(updatedSkuDTO);
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }

    @Test
    @DisplayName("INT: Update Sku when not exists")
    void updateSku_when_not_exists() {

        Sku skuUpdated = Sku.builder().id("S1").name("New_Name").description("Description").build();
        SkuDTO changeSkuDTO = SkuDTO.builder().id("S1").name("New_Name").build();

        BDDMockito.given(repository.save(skuUpdated))
                .willReturn(Mono.just(skuUpdated));

        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.empty());

        webClient.patch()
                .uri("/sku")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(changeSkuDTO))
                .exchange()
                .expectStatus().isNotFound();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }

    @Test
    @DisplayName("INT: Delete Sku")
    void deleteSku(){
        BDDMockito.given(repository.delete(s)).willReturn(Mono.empty());
        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.just(s));

        webClient.delete()
                .uri("/sku/S1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(repository,BDDMockito.atMost(1)).delete(s);
    }

    @Test
    @DisplayName("INT: Delete Sku when not exists")
    void deleteSku_when_not_exists(){
        BDDMockito.given(repository.delete(s)).willReturn(Mono.empty());
        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.empty());

        webClient.delete()
                .uri("/sku/S1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isNotFound();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).delete(s);
    }

    @Test
    @DisplayName("INT: Create Sku")
    void createSku() {
        BDDMockito.given(repository.save(s))
                .willReturn(Mono.just(s));
        BDDMockito
                .given(repository.existsById("S1"))
                .willReturn(Mono.just(false));

        webClient.post()
                .uri("/sku")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(sDTO))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(SkuDTO.class)
                .isEqualTo(sDTO);
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }

    @Test
    @DisplayName("INT: Create Sku when already exists")
    void createSku_when_already_exists() {
        BDDMockito.given(repository.save(s))
                .willReturn(Mono.just(s));
        BDDMockito
                .given(repository.existsById("S1"))
                .willReturn(Mono.just(true));

        webClient.post()
                .uri("/sku")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(sDTO))
                .exchange()
                .expectStatus().isBadRequest();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }
}
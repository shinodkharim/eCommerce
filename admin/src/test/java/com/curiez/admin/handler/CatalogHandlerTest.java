package com.curiez.admin.handler;

import com.curiez.admin.dto.CatalogDTO;
import com.curiez.admin.dto.SkuDTO;
import com.curiez.admin.model.Catalog;
import com.curiez.admin.model.Sku;
import com.curiez.admin.repository.CatalogRepository;
import com.curiez.admin.repository.SkuRepository;
import com.curiez.admin.route.CatalogRouter;
import com.curiez.admin.route.SkuRouter;
import com.curiez.admin.service.CatalogService;
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
@ContextConfiguration(classes = {CatalogRouter.class,CatalogHandler.class, CatalogService.class})
@WebFluxTest
class CatalogHandlerTest {



    @MockBean
    private CatalogRepository repository;
    @Autowired
    private WebTestClient webClient;
    Catalog s;
    CatalogDTO sDTO;

    @BeforeEach
    void setUp() {
        s = Catalog.builder().id("S1").name("Name").description("Description").build();
        sDTO = CatalogDTO.builder().id("S1").name("Name").description("Description").build();
    }

    @Test
    @DisplayName("INT: Create or Save Catalog")
    void saveCatalog() {
        BDDMockito.given(repository.save(s))
                .willReturn(Mono.just(s));

        webClient.put()
                .uri("/catalog")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(sDTO))
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }



    @Test
    @DisplayName("INT: Get SKu")
    void getCatalog() {
        BDDMockito.given(repository.findById("S1")).willReturn(Mono.just(s));

        webClient.get()
                .uri("/catalog/S1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CatalogDTO.class)
                .isEqualTo(sDTO);

        BDDMockito.verify(repository,BDDMockito.atMost(1)).findById("P1");
    }

    @Test
    @DisplayName("INT: Get Catalog when not exists")
    void getCatalog_when_not_exists() {
        BDDMockito.given(repository.findById("S1")).willReturn(Mono.empty());

        webClient.get()
                .uri("/catalog/S1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isNoContent();

        BDDMockito.verify(repository,BDDMockito.atMost(1)).findById("P1");
    }


    @Test
    @DisplayName("INT: Update Catalog ignoring the empty fields")
    void updateCatalog() {

        Catalog catalogUpdated = Catalog.builder().id("S1").name("New_Name").description("Description").build();
        CatalogDTO changeCatalogDTO = CatalogDTO.builder().id("S1").name("New_Name").build();
        CatalogDTO updatedCatalogDTO = CatalogDTO.builder().id("S1").name("New_Name").description("Description").build();

        BDDMockito.given(repository.save(catalogUpdated))
                .willReturn(Mono.just(catalogUpdated));

        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.just(s));

        webClient.patch()
                .uri("/catalog")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(changeCatalogDTO))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CatalogDTO.class)
                .isEqualTo(updatedCatalogDTO);
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }

    @Test
    @DisplayName("INT: Update Catalog when not exists")
    void updateCatalog_when_not_exists() {

        Catalog catalogUpdated = Catalog.builder().id("S1").name("New_Name").description("Description").build();
        CatalogDTO changeCatalogDTO = CatalogDTO.builder().id("S1").name("New_Name").build();

        BDDMockito.given(repository.save(catalogUpdated))
                .willReturn(Mono.just(catalogUpdated));

        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.empty());

        webClient.patch()
                .uri("/catalog")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(changeCatalogDTO))
                .exchange()
                .expectStatus().isNotFound();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }

    @Test
    @DisplayName("INT: Delete Catalog")
    void deleteCatalog(){
        BDDMockito.given(repository.delete(s)).willReturn(Mono.empty());
        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.just(s));

        webClient.delete()
                .uri("/catalog/S1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(repository,BDDMockito.atMost(1)).delete(s);
    }

    @Test
    @DisplayName("INT: Delete Catalog when not exists")
    void deleteCatalog_when_not_exists(){
        BDDMockito.given(repository.delete(s)).willReturn(Mono.empty());
        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.empty());

        webClient.delete()
                .uri("/catalog/S1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isNotFound();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).delete(s);
    }

    @Test
    @DisplayName("INT: Create Catalog")
    void createCatalog() {
        BDDMockito.given(repository.save(s))
                .willReturn(Mono.just(s));
        BDDMockito
                .given(repository.existsById("S1"))
                .willReturn(Mono.just(false));

        webClient.post()
                .uri("/catalog")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(sDTO))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CatalogDTO.class)
                .isEqualTo(sDTO);
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }

    @Test
    @DisplayName("INT: Create Catalog when already exists")
    void createCatalog_when_already_exists() {
        BDDMockito.given(repository.save(s))
                .willReturn(Mono.just(s));
        BDDMockito
                .given(repository.existsById("S1"))
                .willReturn(Mono.just(true));

        webClient.post()
                .uri("/catalog")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(sDTO))
                .exchange()
                .expectStatus().isBadRequest();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(s);
    }
}
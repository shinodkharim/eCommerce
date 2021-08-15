package com.curiez.admin.handler;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.exception.ItemNotFound;
import com.curiez.admin.model.Product;
import com.curiez.admin.repository.ProductRepository;
import com.curiez.admin.route.ProductRouter;
import com.curiez.admin.service.ProductService;
import org.junit.jupiter.api.*;
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
@ContextConfiguration(classes = {ProductRouter.class,ProductHandler.class, ProductService.class})
@WebFluxTest
class ProductHandlerTest {

    @MockBean
    private ProductRepository repository;
    @Autowired
    private WebTestClient webClient;
    Product p;
    ProductDTO pDTO;

    @BeforeEach
    void setUp() {
         p = Product.builder().id("P1").name("Name").description("Description").build();
         pDTO = ProductDTO.builder().id("P1").name("Name").description("Description").build();
    }


    @Test
    @DisplayName("INT: Create or Save Product")
    void saveProduct() {
        BDDMockito.given(repository.save(p))
                .willReturn(Mono.just(p));

        webClient.put()
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(pDTO))
                .exchange()
                .expectStatus().isOk();
                //.expectBody(ProductDTO.class)
              //  .isEqualTo(pDTO);
      BDDMockito.verify(repository,BDDMockito.atMost(1)).save(p);
    }



    @Test
    @DisplayName("INT: Get Product")
    void getProduct() {
        BDDMockito.given(repository.findById("P1")).willReturn(Mono.just(p));

        webClient.get()
                .uri("/product/P1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDTO.class)
                .isEqualTo(pDTO);

        BDDMockito.verify(repository,BDDMockito.atMost(1)).findById("P1");
    }

    @Test
    @DisplayName("INT: Get Product when not exists")
    void getProduct_when_not_exists() {
        BDDMockito.given(repository.findById("P1")).willReturn(Mono.empty());

        webClient.get()
                .uri("/product/P1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isNoContent();

        BDDMockito.verify(repository,BDDMockito.atMost(1)).findById("P1");
    }


    @Test
    @DisplayName("INT: Update Product ignoring the empty fields")
    void updateProduct() {

        Product productUpdated = Product.builder().id("P1").name("New_Name").description("Description").build();
        ProductDTO changeProductDTO = ProductDTO.builder().id("P1").name("New_Name").build();
        ProductDTO updatedProductDTO = ProductDTO.builder().id("P1").name("New_Name").description("Description").build();

        BDDMockito.given(repository.save(productUpdated))
                .willReturn(Mono.just(productUpdated));

        BDDMockito.given(repository.findById("P1"))
                .willReturn(Mono.just(p));

        webClient.patch()
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(changeProductDTO))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDTO.class)
                .isEqualTo(updatedProductDTO);
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(p);
    }

    @Test
    @DisplayName("INT: Update Product when not exists")
    void updateProduct_when_not_exists() {

        Product productUpdated = Product.builder().id("P1").name("New_Name").description("Description").build();
        ProductDTO changeProductDTO = ProductDTO.builder().id("P1").name("New_Name").build();
        ProductDTO updatedProductDTO = ProductDTO.builder().id("P1").name("New_Name").description("Description").build();

        BDDMockito.given(repository.save(productUpdated))
                .willReturn(Mono.just(productUpdated));

        BDDMockito.given(repository.findById("P1"))
                .willReturn(Mono.empty());

        webClient.patch()
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(changeProductDTO))
                .exchange()
                .expectStatus().isNotFound();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(p);
    }

    @Test
    @DisplayName("INT: Delete Product")
    void deleteProduct(){
        BDDMockito.given(repository.delete(p)).willReturn(Mono.empty());
        BDDMockito.given(repository.findById("P1"))
                .willReturn(Mono.just(p));

        webClient.delete()
                .uri("/product/P1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(repository,BDDMockito.atMost(1)).delete(p);
    }

    @Test
    @DisplayName("INT: Delete Product when not exists")
    void deleteProduct_when_not_exists(){
        BDDMockito.given(repository.delete(p)).willReturn(Mono.empty());
        BDDMockito.given(repository.findById("P1"))
                .willReturn(Mono.empty());

        webClient.delete()
                .uri("/product/P1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isNotFound();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).delete(p);
    }

    @Test
    @DisplayName("INT: Create Product")
    void createProduct() {
        BDDMockito.given(repository.save(p))
                .willReturn(Mono.just(p));
        BDDMockito
                .given(repository.existsById("P1"))
                .willReturn(Mono.just(false));

        webClient.post()
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(pDTO))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductDTO.class)
                .isEqualTo(pDTO);
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(p);
    }

    @Test
    @DisplayName("INT: Create Product when already exists")
    void createProduct_when_already_exists() {
        BDDMockito.given(repository.save(p))
                .willReturn(Mono.just(p));
        BDDMockito
                .given(repository.existsById("P1"))
                .willReturn(Mono.just(true));

        webClient.post()
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(pDTO))
                .exchange()
                .expectStatus().isBadRequest();
        BDDMockito.verify(repository,BDDMockito.atMost(1)).save(p);
    }


}
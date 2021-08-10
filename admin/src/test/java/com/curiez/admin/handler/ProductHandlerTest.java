package com.curiez.admin.handler;

import com.curiez.admin.dto.ProductDTO;
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
@ContextConfiguration(classes = {ProductRouter.class,ProductHandler.class,ProductService.class})
@WebFluxTest
class ProductHandlerTest {

    @MockBean
    private ProductRepository productRepository;
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
    void saveProduct() {
        BDDMockito.given(productRepository.save(p))
                .willReturn(Mono.just(p));

        webClient.put()
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(BodyInserters.fromValue(pDTO))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDTO.class)
                .isEqualTo(pDTO);
      BDDMockito.verify(productRepository,BDDMockito.atMost(1)).save(p);
    }



    @Test
    void getProduct() {
        BDDMockito.given(productRepository.findById("P1")).willReturn(Mono.just(p));

        webClient.get()
                .uri("/product/P1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDTO.class)
                .isEqualTo(pDTO);

        BDDMockito.verify(productRepository,BDDMockito.atMost(1)).findById("P1");
    }



    @Test
    void updateProduct() {

        Product productUpdated = Product.builder().id("P1").name("New_Name").description("Description").build();
        ProductDTO changeProductDTO = ProductDTO.builder().id("P1").name("New_Name").build();
        ProductDTO updatedProductDTO = ProductDTO.builder().id("P1").name("New_Name").description("Description").build();

        BDDMockito.given(productRepository.save(productUpdated))
                .willReturn(Mono.just(productUpdated));

        BDDMockito.given(productRepository.findById("P1"))
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
        BDDMockito.verify(productRepository,BDDMockito.atMost(1)).save(p);
    }

    @Test
    void deleteProduct(){
        BDDMockito.given(productRepository.delete(p)).willReturn(Mono.empty());
        BDDMockito.given(productRepository.findById("P1"))
                .willReturn(Mono.just(p));

        webClient.delete()
                .uri("/product/P1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .exchange()
                .expectStatus().isNoContent();

        BDDMockito.verify(productRepository,BDDMockito.atMost(1)).delete(p);
    }

    @Test
    void createProduct() {
        BDDMockito.given(productRepository.save(p))
                .willReturn(Mono.just(p));
        BDDMockito
                .given(productRepository.existsById("P1"))
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
        BDDMockito.verify(productRepository,BDDMockito.atMost(1)).save(p);
    }


}
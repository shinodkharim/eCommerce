package com.curiez.admin.service;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.model.Product;
import com.curiez.admin.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@Tag("UNIT")
@Tag("Service")
class ProductServiceTest {
    ProductDTO  pDto;
    Product pEntity;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
         pEntity = Product.builder().id("P1").name("Name").description("Description").build();
         pDto = ProductDTO.builder().id("P1").name("Name").description("Description").build();

    }

    @AfterEach
    void tearDown() {

    }


    @Test
    @DisplayName("Create or Save the Product and return Mono of ProductDTO")
    public void saveProduct(){
        BDDMockito
                .given(productRepository.save(pEntity))
                .willReturn(Mono.just(pEntity));

        StepVerifier
                .create(service.saveProduct(pDto))
                .expectSubscription()
                .expectNext(pDto)
                .verifyComplete();

        BDDMockito
                .verify(productRepository,BDDMockito.atMost(1))
                .save(pEntity);
    }


    @Test
    @DisplayName("Create the Product and return Mono of ProductDTO")
    public void createProduct(){
        BDDMockito
                .given(productRepository.save(pEntity))
                .willReturn(Mono.just(pEntity));

        BDDMockito
                .given(productRepository.existsById("P1"))
                .willReturn(Mono.just(false));

        StepVerifier
                .create(service.createProduct(pDto))
                .expectSubscription()
                .expectNext(pDto)
                .verifyComplete();

        BDDMockito
                .verify(productRepository,BDDMockito.atMost(1))
                .save(pEntity);
    }

    @Test
    @DisplayName("Get Product by ID and returns Mono of ProductDTO")
    public void getProduct(){
        pEntity = Product.builder().id("P1").name("Name").description("Description").build();
        BDDMockito
                .given(productRepository.findById("P1"))
                .willReturn(Mono.just(pEntity));

        StepVerifier
                .create(service.getProduct("P1"))
                .expectSubscription()
                .expectNext(pDto)
                .verifyComplete();
        BDDMockito
                .verify(productRepository,BDDMockito.atMost(1))
                .findById("P1");
    }

    @Test
    @DisplayName("Partial update product and returns Mono of ProductDTO")
    public void updateProduct(){
        Product productUpdated = Product.builder().id("P1").name("New_Name").description("Description").build();
        ProductDTO changeProductDTO = ProductDTO.builder().id("P1").name("New_Name").build();
        ProductDTO updatedProductDTO = ProductDTO.builder().id("P1").name("New_Name").description("Description").build();
        BDDMockito.given(productRepository.findById("P1"))
                        .willReturn(Mono.just(pEntity));

        BDDMockito
                .given(productRepository.save(productUpdated))
                .willReturn(Mono.just(productUpdated));

        StepVerifier.create(service.updateProduct(changeProductDTO))
                    .expectSubscription()
                    .expectNext(updatedProductDTO)
                    .verifyComplete();

        BDDMockito
                .verify(productRepository,BDDMockito.atMost(1))
                .save(pEntity);

    }
    @Test
    @DisplayName("Delete the product and returns Mono of void")
    public void deleteProduct(){
        BDDMockito.given(productRepository.findById("P1"))
                .willReturn(Mono.just(pEntity));

        BDDMockito.given(productRepository.delete(pEntity))
                .willReturn(Mono.empty());

        StepVerifier.create(service.deleteProduct("P1"))
                .expectSubscription()
                .verifyComplete();
        BDDMockito.verify(productRepository,BDDMockito.atMost(1))
                .delete(pEntity);
    }



}
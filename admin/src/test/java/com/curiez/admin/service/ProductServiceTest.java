package com.curiez.admin.service;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.exception.ItemExists;
import com.curiez.admin.exception.ItemNotFound;
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
@Tag("SERVICE")
class ProductServiceTest {
    ProductDTO dto;
    Product entity;

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
         entity = Product.builder().id("P1").name("Name").description("Description").build();
         dto = ProductDTO.builder().id("P1").name("Name").description("Description").build();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    @DisplayName("Create or Save the Product and return Mono of ProductDTO")
    public void saveProduct(){
        BDDMockito
                .given(repository.save(entity))
                .willReturn(Mono.just(entity));

        StepVerifier
                .create(service.save(dto))
                .expectSubscription()
                .expectNext(dto)
                .verifyComplete();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);
    }


    @Test
    @DisplayName("Create the Product and return Mono of ProductDTO")
    public void createProduct(){
        BDDMockito
                .given(repository.save(entity))
                .willReturn(Mono.just(entity));

        BDDMockito
                .given(repository.existsById("P1"))
                .willReturn(Mono.just(false));

        StepVerifier
                .create(service.create(dto, dto.getId()))
                .expectSubscription()
                .expectNext(dto)
                .verifyComplete();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);
    }

    @Test
    @DisplayName("Create the Product when exits throws ItemExists")
    public void createProduct_when_exists(){
        BDDMockito
                .given(repository.save(entity))
                .willReturn(Mono.just(entity));

        BDDMockito
                .given(repository.existsById("P1"))
                .willReturn(Mono.just(true));

        StepVerifier
                .create(service.create(dto, dto.getId()))
                .expectSubscription()
                .expectError(ItemExists.class)
                .verify();


        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);
    }



    @Test
    @DisplayName("Get Product by an invalid ID returns ItemNotFound")
    public void getProduct_when_not_exists(){
        entity = Product.builder().id("P1").name("Name").description("Description").build();
        BDDMockito
                .given(repository.findById("INVALID_ID"))
                .willReturn(Mono.empty());

        StepVerifier
                .create(service.find("INVALID_ID"))
                .expectSubscription()
                .expectError(ItemNotFound.class)
                .verify();
        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .findById("P1");
    }

    @Test
    @DisplayName("Get Product by ID returns Mono of Product DTO")
    public void getProduct(){

        BDDMockito
                .given(repository.findById("P1"))
                .willReturn(Mono.just(entity));

        StepVerifier
                .create(service.find("P1"))
                .expectSubscription()
                .expectNext(dto)
                .verifyComplete();
        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .findById("P1");
    }


    @Test
    @DisplayName("Partial update product and returns Mono of ProductDTO")
    public void updateProduct(){
        Product productUpdated = Product.builder().id("P1").name("New_Name").description("Description").build();
        ProductDTO changeProductDTO = ProductDTO.builder().id("P1").name("New_Name").build();
        ProductDTO updatedProductDTO = ProductDTO.builder().id("P1").name("New_Name").description("Description").build();
        BDDMockito.given(repository.findById("P1"))
                        .willReturn(Mono.just(entity));

        BDDMockito
                .given(repository.save(productUpdated))
                .willReturn(Mono.just(productUpdated));

        StepVerifier.create(service.update(changeProductDTO,changeProductDTO.getId()))
                    .expectSubscription()
                    .expectNext(updatedProductDTO)
                    .verifyComplete();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);

    }

    @Test
    @DisplayName("Partial update invalid product throws ItemNotFound Exception")
    public void updateProduct_when_not_found(){
        Product product = Product.builder().id("INVALID_ID").name("New_Name").description("Description").build();
        ProductDTO productDTO = ProductDTO.builder().id("INVALID_ID").name("New_Name").description("Description").build();
        BDDMockito.given(repository.findById("INVALID_ID"))
                .willReturn(Mono.empty());

        BDDMockito
                .given(repository.save(product))
                .willReturn(Mono.just(product));

        StepVerifier.create(service.update(productDTO,productDTO.getId()))
                .expectSubscription()
                .expectError(ItemNotFound.class)
                .verify();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);

    }


    @Test
    @DisplayName("Delete the product when not exists throws ItemNotFound")
    public void deleteProduct(){
        BDDMockito.given(repository.findById("INVALID_ID"))
                .willReturn(Mono.empty());

        BDDMockito.given(repository.delete(entity))
                .willReturn(Mono.empty());

        StepVerifier.create(service.delete("INVALID_ID"))
                .expectSubscription()
                        .expectError(ItemNotFound.class)
                                .verify();
        BDDMockito.verify(repository,BDDMockito.atMost(1))
                .delete(entity);
    }



}
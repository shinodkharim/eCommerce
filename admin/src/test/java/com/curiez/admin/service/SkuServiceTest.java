package com.curiez.admin.service;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.dto.SkuDTO;
import com.curiez.admin.exception.ItemExists;
import com.curiez.admin.exception.ItemNotFound;
import com.curiez.admin.model.Product;
import com.curiez.admin.model.Sku;
import com.curiez.admin.repository.SkuRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@Tag("UNIT")
@Tag("SERVICE")
class SkuServiceTest {

    private Sku entity;
    private SkuDTO dto;
    @Mock
    private SkuRepository repository;
    @InjectMocks
    private SkuService service;

    @BeforeEach
    void setUp() {
        entity = Sku.builder().id("P1").name("Name").description("Description").build();
        dto = SkuDTO.builder().id("P1").name("Name").description("Description").build();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    @DisplayName("Create or Save the SKU and return Mono of ProductDTO")
    public void saveSku(){
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
    @DisplayName("Create the SKU and return Mono of SKU DTO")
    public void createSku(){
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
    @DisplayName("Create the SKU when exits throws ItemExists")
    public void createSku_when_exists(){
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
    @DisplayName("Get SKU by an invalid ID returns ItemNotFound")
    public void getSku_when_not_exists(){
        entity = Sku.builder().id("P1").name("Name").description("Description").build();
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
    @DisplayName("Get SKU by ID returns Mono of SKU DTO")
    public void getSku(){

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
    @DisplayName("Partial update SKU and returns Mono of SKU DTO")
    public void updateSku(){
        Sku sku = Sku.builder().id("S1").name("New_Name").description("Description").build();
        SkuDTO skuDto = SkuDTO.builder().id("S1").name("New_Name").build();
        SkuDTO updatedSkuDTO = SkuDTO.builder().id("S1").name("New_Name").description("Description").build();

        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.just(entity));

        BDDMockito
                .given(repository.save(sku))
                .willReturn(Mono.just(sku));

        StepVerifier.create(service.update(skuDto,skuDto.getId()))
                .expectSubscription()
                .expectNext(updatedSkuDTO)
                .verifyComplete();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);

    }

    @Test
    @DisplayName("Partial update invalid SKU throws ItemNotFound Exception")
    public void updateSku_when_not_found(){
        Sku sku = Sku.builder().id("INVALID_ID").name("New_Name").description("Description").build();
        SkuDTO skuDTO = SkuDTO.builder().id("INVALID_ID").name("New_Name").description("Description").build();
        BDDMockito.given(repository.findById("INVALID_ID"))
                .willReturn(Mono.empty());

        BDDMockito
                .given(repository.save(sku))
                .willReturn(Mono.just(sku));

        StepVerifier.create(service.update(skuDTO,skuDTO.getId()))
                .expectSubscription()
                .expectError(ItemNotFound.class)
                .verify();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);

    }


    @Test
    @DisplayName("Delete the SKU when not exists throws ItemNotFound")
    public void deleteSku(){
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
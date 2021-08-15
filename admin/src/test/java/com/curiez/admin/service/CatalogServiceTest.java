package com.curiez.admin.service;

import com.curiez.admin.dto.CatalogDTO;
import com.curiez.admin.dto.SkuDTO;
import com.curiez.admin.exception.ItemExists;
import com.curiez.admin.exception.ItemNotFound;
import com.curiez.admin.model.Catalog;
import com.curiez.admin.model.Sku;
import com.curiez.admin.repository.CatalogRepository;
import com.curiez.admin.repository.SkuRepository;
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
class CatalogServiceTest {

    private Catalog entity;
    private CatalogDTO dto;
    @Mock

    private CatalogRepository repository;
    @InjectMocks
    private CatalogService service;

    @BeforeEach
    void setUp() {
        entity = Catalog.builder().id("P1").name("Name").description("Description").build();
        dto = CatalogDTO.builder().id("P1").name("Name").description("Description").build();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    @DisplayName("Create or Save the SKU and return Mono of ProductDTO")
    public void saveCatalog(){
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
    @DisplayName("Create the Catalog and return Mono of Catalog DTO")
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
    @DisplayName("Create the Catalog when exits throws ItemExists")
    public void createCatalog_when_exists(){
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
    @DisplayName("Get Catalog by an invalid ID returns ItemNotFound")
    public void getCatalog_when_not_exists(){
        entity = Catalog.builder().id("P1").name("Name").description("Description").build();
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
    @DisplayName("Get Catalog by ID returns Mono of Catalog  DTO")
    public void getCatalog(){

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
    @DisplayName("Partial update Catalog and returns Mono of Catalog DTO")
    public void updateCatalog(){
        Catalog catalog = Catalog.builder().id("S1").name("New_Name").description("Description").build();
        CatalogDTO catalogDto = CatalogDTO.builder().id("S1").name("New_Name").build();
        CatalogDTO updatedCatalogDto = CatalogDTO.builder().id("S1").name("New_Name").description("Description").build();

        BDDMockito.given(repository.findById("S1"))
                .willReturn(Mono.just(entity));

        BDDMockito
                .given(repository.save(catalog))
                .willReturn(Mono.just(catalog));

        StepVerifier.create(service.update(catalogDto,catalogDto.getId()))
                .expectSubscription()
                .expectNext(updatedCatalogDto)
                .verifyComplete();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);

    }

    @Test
    @DisplayName("Partial update invalid SKU throws ItemNotFound Exception")
    public void updateCatalog_when_not_found(){
        Catalog catalog = Catalog.builder().id("INVALID_ID").name("New_Name").description("Description").build();
        CatalogDTO catalogDTO = CatalogDTO.builder().id("INVALID_ID").name("New_Name").description("Description").build();
        BDDMockito.given(repository.findById("INVALID_ID"))
                .willReturn(Mono.empty());

        BDDMockito
                .given(repository.save(catalog))
                .willReturn(Mono.just(catalog));

        StepVerifier.create(service.update(catalogDTO,catalogDTO.getId()))
                .expectSubscription()
                .expectError(ItemNotFound.class)
                .verify();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(entity);

    }


    @Test
    @DisplayName("Delete the Catalog when not exists throws ItemNotFound")
    public void deleteCatalog(){
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
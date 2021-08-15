package com.curiez.admin.service;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.model.Product;
import com.curiez.admin.service.mapper.DomainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@Tag("UNIT")
@Tag("Service")
class BasicCRUDServiceTest {

    ProductDTO pDto;
    Product pEntity;

    @Mock
    private ReactiveCrudRepository repository;

    @Mock
    private DomainMapper mapper;

    @Mock
    private BasicCRUDService service;

    @BeforeEach
    void setUp() {
        pEntity = Product.builder().id("P1").name("Name").description("Description").build();
        pDto = ProductDTO.builder().id("P1").name("Name").description("Description").build();

    }

    @Test
    void save() {
        BDDMockito.given(service.getRepository()).willReturn(repository);
        BDDMockito.given(service.getMapper()).willReturn(mapper);
        BDDMockito.when(service.save(pDto)).thenReturn(Mono.just(pDto));
       // BDDMockito.when(service.getRepository()).thenCallRealMethod();
       // BDDMockito.when(service.getMapper()).thenCallRealMethod();

        BDDMockito
                .given(repository.save(pEntity))
                .willReturn(Mono.just(pEntity));

        StepVerifier
                .create(service.save(pDto))
                .expectSubscription()
                .expectNext(pDto)
                .verifyComplete();

        BDDMockito
                .verify(repository,BDDMockito.atMost(1))
                .save(pEntity);
    }

    @Test
    void create() {
    }

    @Test
    void find() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getRepository() {
    }

    @Test
    void getMapper() {
    }
}
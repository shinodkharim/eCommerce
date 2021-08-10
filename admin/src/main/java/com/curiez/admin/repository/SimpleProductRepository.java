package com.curiez.admin.repository;




import com.curiez.admin.model.*;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.*;

@Repository
public class SimpleProductRepository {

    public Mono<Product> findById(String id){
    return Mono.just(getProduct());
    }
    public Mono<Sku> findSkuById(String id){
        return Mono.just(getSku());
    }
    public Mono<Void> save(Product product){
    return Mono.empty();
    }


    public Product getProduct(){
        Product product = new Product();
        product.setId("P1");
        product.setName("Jeans");
        product.setDescription("Levis Collection Denim Jeans!");
        Set<Media> medias = new HashSet<>();
        Media pdpImage = new Media();
        pdpImage.setId("img1");
        pdpImage.setUrl("https://sourceofheat.store/wp-content/uploads/2021/03/IMG_7109.jpg");
        pdpImage.setAltText("Levis Blue Jeans");
        pdpImage.setType("PDP");
        medias.add(pdpImage);
        product.setMedias(medias);
        Map<String,String> attributes = new HashMap<String,String>();
        attributes.put("waist","32");
        attributes.put("fit","Regular");
        product.setAttributes(attributes);
        Set<Sku> skus = new HashSet<Sku>();
        skus.add(getSku());
        product.setSkus(skus);
        product.setList(2.9);
        product.setSale(2.6);
        return product;
    }

    public Sku getSku(){
        Sku sku = new Sku();
    sku.setId("S1P1");
    sku.setName("Levis Blue Jeans");
    sku.setDescription("fine crafted blue jeans");
    sku.setIsStock(true);
        Set<Media> medias = new HashSet<Media>();
        Media pdpImage = new Media();
        pdpImage.setId("sku-img1");
        pdpImage.setUrl("https://sourceofheat.store/wp-content/uploads/2021/03/IMG_7109.jpg");
        pdpImage.setAltText("Levis Blue Jeans");
        pdpImage.setType("SKU_IMG");
        medias.add(pdpImage);
        sku.setMedias(medias);
        Map<String,String> attributes = new HashMap<String,String>();
        attributes.put("color"," Blue");
        attributes.put("material","Stretchable");
        sku.setAttributes(attributes);
        sku.setId("s-price1");
        sku.setList(1.9);
        sku.setSale(1.6);
        return sku;
    }

}

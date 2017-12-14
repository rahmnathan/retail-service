package com.github.rahmnathan.retail.price.data.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductPriceRepository extends MongoRepository<ProductPriceEntity, Long> {
}

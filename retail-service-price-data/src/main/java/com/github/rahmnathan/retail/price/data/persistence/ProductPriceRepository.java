package com.github.rahmnathan.retail.price.data.persistence;

import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

public interface ProductPriceRepository extends MongoRepository<ProductPrice, Long> {
}

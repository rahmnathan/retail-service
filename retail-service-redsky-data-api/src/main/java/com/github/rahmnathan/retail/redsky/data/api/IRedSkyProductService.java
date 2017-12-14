package com.github.rahmnathan.retail.redsky.data.api;

import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
import com.github.rahmnathan.retail.redsky.data.exception.RedSkyServiceException;

import java.util.Optional;

public interface IRedSkyProductService {
    Optional<RedSkyProduct> getRedSkyProduct(Long id) throws RedSkyServiceException;
}

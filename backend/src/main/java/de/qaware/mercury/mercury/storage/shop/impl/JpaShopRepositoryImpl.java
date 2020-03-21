package de.qaware.mercury.mercury.storage.shop.impl;

import de.qaware.mercury.mercury.business.shop.Shop;
import de.qaware.mercury.mercury.storage.shop.ShopRepository;
import de.qaware.mercury.mercury.util.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
class JpaShopRepositoryImpl implements ShopRepository {
    private final ShopDataRepository shopDataRepository;

    JpaShopRepositoryImpl(ShopDataRepository shopDataRepository) {
        this.shopDataRepository = shopDataRepository;
    }

    @Override
    public List<Shop> listAll() {
        return Lists.map(shopDataRepository.findAll(), ShopEntity::toShop);
    }

    @Override
    public void insert(Shop shop) {
        log.debug("Insert {}", shop);
        shopDataRepository.save(ShopEntity.of(shop));
    }

    @Override
    public Shop findById(Shop.Id id) {
        log.debug("Find Shop {}", id);
        Optional<ShopEntity> shopEntityOptional = shopDataRepository.findById(id.getId());
        ShopEntity shopEntity = shopEntityOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return shopEntity.toShop();
    }

    @Override
    public List<Shop> findNearby(double longitude, double latitude) {
        log.debug("Find show nearby location {}, {}", longitude, latitude);

        Collection<ShopEntity> shops = Collections.emptyList();

        // TODO:  implement this query correctly and transform miles to kilometers
        // first step should use limit 10 and no max distance

/*        SELECT latitude, longitude, SQRT(
            POW(69.1 * (latitude - [startlat]), 2) +
            POW(69.1 * ([startlng] - longitude) * COS(latitude / 57.3), 2)) AS distance
        FROM TableName HAVING distance < maxDistance ORDER BY distance LIMIT maxResults;*/


        return Lists.map(shops, ShopEntity::toShop);
    }
}

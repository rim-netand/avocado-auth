package com.netand.avocado.authorization.redis.repository;

import com.netand.avocado.authorization.redis.entity.AccessTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccessTokenRepository extends PagingAndSortingRepository< AccessTokenEntity, String > {
}

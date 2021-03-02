package com.netand.avocado.authorization.redis.repository;

import com.netand.avocado.authorization.redis.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository< RefreshTokenEntity, String > {


}

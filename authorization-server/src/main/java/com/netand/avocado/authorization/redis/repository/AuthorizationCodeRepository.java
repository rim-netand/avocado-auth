package com.netand.avocado.authorization.redis.repository;

import com.netand.avocado.authorization.redis.entity.AuthorizationCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorizationCodeRepository extends CrudRepository< AuthorizationCodeEntity, String > {
}

package com.ad.simpleLib.services;

import java.util.List;
import java.util.Optional;

import com.ad.simpleLib.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

	List<AuthorEntity> findAll();

	Optional<AuthorEntity> findOne(Long id);

	boolean isExists(Long id);
}
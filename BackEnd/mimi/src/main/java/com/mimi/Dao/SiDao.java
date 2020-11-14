package com.mimi.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mimi.Dto.Si;

@Repository
public interface SiDao extends MongoRepository<Si, Object> {

}

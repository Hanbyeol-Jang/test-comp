package com.mimi.Dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mimi.Dto.Sigun;

@Repository
public interface SigunDao extends MongoRepository<Sigun, Object> {

	List<Sigun> findBySi(String si);

}

package com.mimi.Dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mimi.Dto.Sigundong;

public interface SigundongDao extends MongoRepository<Sigundong, Object> {

	List<Sigundong> findBySiAndGun(String si, String gun);

}

package com.mimi.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mimi.Dto.Review;

public interface ReviewDao extends MongoRepository<Review, Integer> {
	public Page<Review> findByResId(int id, Pageable pageable);
	public List<Review> findByUserIdOrderByIdDesc(String userId);
}

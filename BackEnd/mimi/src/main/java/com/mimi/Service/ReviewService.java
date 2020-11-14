package com.mimi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mimi.Dao.ReviewDao;
import com.mimi.Dto.Review;

@Service
public class ReviewService {
	@Autowired
	private ReviewDao reviewDao;
	
	public Review save(Review review) {
		reviewDao.save(review);
		return review;
	}
	
	public List<Review> findAll() {
		return reviewDao.findAll();
	}
	
	public Page<Review> findByResId(int id, Pageable pageable){
		return reviewDao.findByResId(id, pageable);
	}
	
	public List<Review> findByUserId(String id){
		return reviewDao.findByUserIdOrderByIdDesc(id);
	}
	
}

package com.mimi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mimi.Dao.ReviewdataDao;
import com.mimi.Dto.Reviewdata;

@Service
public class ReviewdataService {
	@Autowired
	private ReviewdataDao reviewdataDao;
	
	public Reviewdata save(Reviewdata review) {
		reviewdataDao.save(review);
		return review;
	}
	
	public List<Reviewdata> findAll() {
		return reviewdataDao.findAll();
	}
	
}

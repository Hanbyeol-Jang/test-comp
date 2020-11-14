package com.mimi.Controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dao.ReviewDao;
import com.mimi.Dto.Review;
import com.mimi.Dto.Reviewdata;
import com.mimi.Service.ReviewService;
import com.mimi.Service.ReviewdataService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@PostMapping(value = "/write")
	@ApiOperation(value = "리뷰남기기 < id 항목 지우고서 넘길 것 >")
	public ResponseEntity<?> write(@RequestBody Review reviewreq) {
		System.out.println("review Controller");
		try {
			reviewService.save(reviewreq);
			
			return new ResponseEntity<>(reviewreq, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "리뷰 레스토랑 ID 기준 페이징 읽기")
	public ResponseEntity<?> read(@PathVariable("id") int id, @RequestParam int pageno) {
		System.out.println("review Controller");
		try {
			
			Pageable request = PageRequest.of(pageno, 16);
			Page<Review> list = reviewService.findByResId(id, request);
			
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
	
}

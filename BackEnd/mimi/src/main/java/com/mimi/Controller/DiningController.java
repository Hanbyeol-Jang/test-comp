package com.mimi.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Dining;
import com.mimi.Dto.Party;
import com.mimi.Dto.TenderInfo;
import com.mimi.Dto.User;
import com.mimi.Service.DiningService;
import com.mimi.Service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dining")
public class DiningController {

	@Autowired
	private DiningService diningService;

	@Autowired
	private UserService userService;

	@PostMapping("/upload")
	@ApiOperation(value = "경매 등록")
	public ResponseEntity<?> upload(@RequestBody Dining dining) {
		System.out.println("upload Controller");
		System.out.println(dining.getDnName() + " " + dining.getDnLocation());

		try {
			// dining 에 추가
			dining.setStoreList(new ArrayList<TenderInfo>());
			Dining diningTender = diningService.upload(dining);

			// user 에 추가
			User user = userService.getUserinfo(dining.getUser().getId()).get();

			List<String> list = user.getDiningList();

			list.add(diningTender.getId());
			user.setDiningList(list);
			userService.update(user);

			return new ResponseEntity<Dining>(diningTender, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "id로 경매 정보 가져오기")
	public ResponseEntity<?> getDining(@PathVariable("id") String id) {
		System.out.println("dining Controller");
		try {
			Dining diningInfo = diningService.getDining(id);

			return new ResponseEntity<Dining>(diningInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/list/{id}")
	@ApiOperation(value = "유저 id로 Dining 정보 목록 가져오기")
	public ResponseEntity<?> getDininglist(@PathVariable("id") String id) {
		System.out.println("getDininglist Controller");

		try {
			// party id 만 가져옴 -> party 모든 정보 가져와야함
			List<String> list = diningService.diningList(id);

			List<Dining> diningList = new ArrayList<>();
			System.out.println(list);
			Dining diningInfo = new Dining();

			for (int i = 0; i < list.size(); i++) {
				diningInfo = diningService.getDining(list.get(i));
				diningList.add(diningInfo);
			}

			return new ResponseEntity<List<Dining>>(diningList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "getboss/{id}")
	@ApiOperation(value = "보스 id로 경매 정보 가져오기")
	public ResponseEntity<?> getBossDining(@PathVariable("id") String boID) {
		System.out.println("dining Controller");
		try {
			List<Dining> diningInfo = diningService.getDiningByBoss(boID);

			return new ResponseEntity<>(diningInfo, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/remove")
	@ApiOperation(value = "경매 취소")
	public ResponseEntity<HashMap<String, Object>> remove(@RequestBody Dining dining) {
		System.out.println("remove Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			diningService.remove(dining);
			map.put("dining", "removed");

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/select")
	@ApiOperation(value = "해당 경매 낙찰")
	public ResponseEntity<HashMap<String, Object>> select(@RequestBody Dining dining) {
		System.out.println("select Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			diningService.remove(dining);
			map.put("dining", "removed");

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

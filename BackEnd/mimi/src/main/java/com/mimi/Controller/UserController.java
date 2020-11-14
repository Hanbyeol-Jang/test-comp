package com.mimi.Controller;

import java.util.HashMap;
import java.util.LinkedList;
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

import com.mimi.Dto.Review;
import com.mimi.Dto.User;
import com.mimi.Service.ReviewService;
import com.mimi.Service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ReviewService reviewService;

	@PostMapping(value = "/login")
	@ApiOperation(value = "로그인 (DB에 없으면 회원가입)")
	public ResponseEntity<HashMap<String, Object>> login(@RequestBody User user) {
		System.out.println("login Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();

			String id = user.getId();

			// id 가 db 에 있는지 확인
			boolean loginChecked = userService.login(id);
			System.out.println(user.toString());
			Optional<User> userinfo = userService.getUserinfo(id);

			// 있으면 로그인
			if (loginChecked) {
				String survey = userinfo.get().getIsSurvey();

				// survey 했는지 확인
				if (survey.equals("true")) {
					map.put("survey", true);
				} else {
					map.put("survey", false);
				}
			} else {
				// db에 없으면 회원 가입
				user.setIsSurvey("false");
				System.out.println(user.toString());
				user.setDiningList(new LinkedList<String>());
				user.setPartyList(new LinkedList<String>());
				userService.join(user);

				map.put("survey", false);
			}

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

//	@PostMapping("/join")
//	@ApiOperation(value = "회원 가입")
//	public ResponseEntity<HashMap<String, Object>> join(@RequestBody User user) {
//		System.out.println("join Controller");
//		System.out.println(user.getId() + " " + user.getUName());
//
//		try {
//			HashMap<String, Object> map = new HashMap<>();
//			User userJoined = userService.join(user);
//
//			// id, 닉네임 중복 체크
//			if (userJoined == null) {
//				map.put("User", "fail");
//			} else {
//				map.put("User", userJoined);
//			}
//
//			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//	}

	@GetMapping(value = "/user/{id}")
	@ApiOperation(value = "id로 회원 정보 가져오기")
	public ResponseEntity<?> getUserinfo(@PathVariable("id") String id) {
		System.out.println("getUserinfo Controller");
		try {
			User userinfo = userService.getUserinfo(id).get();

			return new ResponseEntity<User>(userinfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/deleteaccount")
	@ApiOperation(value = "회원 탈퇴")
	public ResponseEntity<HashMap<String, Object>> deleteAccount(@RequestBody User user) {
		System.out.println("deleteAccount Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			userService.deleteAccount(user);
			map.put("Userinfo", "deleted");

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/deviceId")
	@ApiOperation(value = "id기반으로 User 검색 후 deviceId 수정")
	public ResponseEntity<HashMap<String, Object>> inputDeviceId(@RequestBody User user) {
		System.out.println("inputDeviceId Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();

			Optional<User> userinfo = userService.getUserinfo(user.getId());
			userinfo.get().setDevice(user.getDevice());
			userService.join(userinfo.get());

			map.put("User", userinfo.get());

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/review/{id}")
	@ApiOperation(value = "id로 리뷰정보 가져오기")
	public ResponseEntity<?> getUserReviews(@PathVariable("id") String id) {
		System.out.println("getUserinfo Controller");
		try {
			List<Review> list = reviewService.findByUserId(id);

			return new ResponseEntity<List<Review>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

}

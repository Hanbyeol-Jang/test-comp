package com.mimi.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mimi.Dto.Party;
import com.mimi.Dto.PartyRequest;
import com.mimi.Dto.PartyResponse;
import com.mimi.Dto.PromiseRequest;
import com.mimi.Dto.User;
import com.mimi.Service.PartyService;
import com.mimi.Service.StoreService;
import com.mimi.Service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/party")
public class PartyController {

	@Autowired
	private PartyService partyService;

	@Autowired
	private UserService userService;

	@Autowired
	private StoreService storeService;

	@PostMapping("/create")
	@ApiOperation(value = "모임 생성")
	public ResponseEntity<?> createParty(@RequestBody PartyRequest partyReq) {
		// user id
		// party name
		System.out.println("createParty Controller");

		try {
			User user = userService.getUserinfo(partyReq.getUserID()).get();
			Party party = partyService.createParty(partyReq);

			List<String> list = user.getPartyList();

			list.add(party.getId());
			user.setPartyList(list);
			userService.update(user);

			return new ResponseEntity<Party>(party, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "party id로 상세정보 가져오기")
	public ResponseEntity<PartyResponse> getParty(@PathVariable("id") String id) {
		System.out.println("getParty Controller");
		try {
			Party partyInfo = partyService.getParty(id);
			PartyResponse partyres = makePartyRes(partyInfo);
			return new ResponseEntity<PartyResponse>(partyres, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private PartyResponse makePartyRes(Party partyInfo) {
		PartyResponse partyres = new PartyResponse();
		partyres.setId(partyInfo.getId());
		partyres.setPromiseLocation(partyInfo.getPromiseLocation());
		partyres.setPromiseStore(partyInfo.getPromiseStore());
		partyres.setPtName(partyInfo.getPtName());
		partyres.setUserList(partyInfo.getUserList());

		if (partyInfo.getPromiseTime() == null) {
			return partyres;
		}
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
		String dateString = transFormat.format(partyInfo.getPromiseTime());
		partyres.setPromiseTime(dateString);
		return partyres;
	}

	@PostMapping(value = "/join")
	@ApiOperation(value = "모임 참여하기")
	public ResponseEntity<?> joinParty(@RequestParam String userId, @RequestParam String partyId) {
		System.out.println("joinParty Controller");

		try {
			Party party = partyService.joinParty(userId, partyId);
			PartyResponse partyres = makePartyRes(party);
			return new ResponseEntity<>(party, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/location")
	@ApiOperation(value = "모임 위치 수정하기")
	public ResponseEntity<?> updateLocation(@RequestParam String partyId, @RequestParam String location) {
		System.out.println("joinParty Controller");

		try {
			Party party = partyService.getParty(partyId);
			party.setPromiseLocation(location);
			return new ResponseEntity<>(party, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@PostMapping(value = "/out")
//	@ApiOperation(value = "모임 탈퇴하기")
//	public ResponseEntity<?> outParty(@RequestParam String uid, @RequestParam String pid) {
//		System.out.println("outParty Controller");
//		try {
//			Party party = partyService.getParty(pid);
//			User user = userService.getUserinfo(uid).get();
//
//			List<Integer> updatePartyList = user.getPartyList();
//			for (int i = 0; i < updatePartyList.size(); i++) {
//				if (updatePartyList.get(i) == Integer.parseInt(pid)) {
//					updatePartyList.remove(i);
//					break;
//				}
//			}
//			user.setPartyList(updatePartyList);
//
//			List<Integer> updateUserList = party.getUserList();
//			for (int i = 0; i < updateUserList.size(); i++) {
//				if (updateUserList.get(i) == Integer.parseInt(uid)) {
//					updateUserList.remove(i);
//					break;
//				}
//			}
//			party.setUserList(updateUserList);
//			partyService.save(party);
//			userService.update(user);
//
//			return new ResponseEntity<>("모임 탈퇴 완료", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//	}

	@GetMapping(value = "/list/{id}")
	@ApiOperation(value = "유저 id로 party 정보 목록 가져오기")
	public ResponseEntity<?> getPartylist(@PathVariable("id") String id) {
		System.out.println("getPartylist Controller");

		try {
			// party id 만 가져옴 -> party 모든 정보 가져와야함
			List<String> list = partyService.partyList(id);
			System.out.println("partyList_test");
//			System.out.println(list);

			List<PartyResponse> partyList = new ArrayList<>();
			for (int i = list.size() - 1; i > 0; i--) {
				Party partyInfo = partyService.getParty(list.get(i));
				PartyResponse partyres = makePartyRes(partyInfo);
				partyList.add(partyres);
			}

			SimpleDateFormat fm = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");

			Collections.sort(partyList, new Comparator<PartyResponse>() {
				@Override
				public int compare(PartyResponse p1, PartyResponse p2) {
					if (p1.getPromiseTime() == null && p2.getPromiseTime() == null) {
						return 0;
					} else if (p1.getPromiseTime() == null && p2.getPromiseTime() != null) {
						return 1;
					} else if (p1.getPromiseTime() != null && p2.getPromiseTime() == null) {
						return -1;
					} else {
						Date date1 = null;
						Date date2 = null;
						try {
							date1 = fm.parse(p1.getPromiseTime());
							date2 = fm.parse(p2.getPromiseTime());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return date1.compareTo(date2);
					}
				}
			});

			System.out.println("partygetParty_test");

			return new ResponseEntity<List<PartyResponse>>(partyList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/delete")
	@ApiOperation(value = "모임 삭제")
	public ResponseEntity<HashMap<String, Object>> deleteParty(@RequestBody Party party) {
		System.out.println("deleteParty Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			partyService.deleteParty(party);
			map.put("Party", "deleted");

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 약속

	@PostMapping(value = "/promiseUpdate")
	@ApiOperation(value = "약속 생성 및 수정")
	public ResponseEntity<?> promiseUpdate(@Valid @RequestBody PromiseRequest promiseReq) {
		System.out.println("약속 생성 및 수정");
		try {
			Party party = partyService.getParty(promiseReq.getPid());
			party.setPromiseStore(storeService.getStore(promiseReq.getStoreid()));
			SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd HHmm");
			String dateString = String.valueOf(promiseReq.getYear()) + exchange(promiseReq.getMonth())
					+ exchange(promiseReq.getDay()) + " " + exchange(promiseReq.getHour())
					+ exchange(promiseReq.getMin());
			Date date = fm.parse(dateString);
			party.setPromiseTime(date);
			partyService.save(party);
			return new ResponseEntity<>(party, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String exchange(int num) {
		String temp = "";
		if (num < 10) {
			temp = "0";
		}
		temp += num;
		return temp;
	}

	@PostMapping(value = "/promiseDelete")
	@ApiOperation(value = "약속 삭제")
	public ResponseEntity<?> promiseDelete(@RequestParam String pid) {
		System.out.println("약속 삭제");
		try {
			Party party = partyService.getParty(pid);
			party.setPromiseStore(null);
			party.setPromiseTime(null);
			partyService.save(party);
			return new ResponseEntity<>(party, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

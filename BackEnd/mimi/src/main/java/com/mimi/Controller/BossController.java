package com.mimi.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Boss;
import com.mimi.Dto.Dining;
import com.mimi.Dto.TenderInfo;
import com.mimi.Service.BossService;
import com.mimi.Service.DiningService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/boss")
public class BossController {

	@Autowired
	private BossService bossService;

	@Autowired
	private DiningService diningService;

	@PostMapping("/create")
	@ApiOperation(value = "사장님 등록")
	public ResponseEntity<HashMap<String, Object>> createBoss(@RequestBody Boss boss) {
		System.out.println("upload Controller");
		System.out.println(boss.getId());

		try {
			HashMap<String, Object> map = new HashMap<>();
			boss.setDiningList(new LinkedList<TenderInfo>());
			
			Boss bossCreated = bossService.createBoss(boss);

			if (boss == null) {
				map.put("boss", "fail");
			} else {
				map.put("boss", bossCreated);
			}

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

//	@GetMapping(value = "/check/{id}")
//	@ApiOperation(value = "사장님 id 로 확인")
//	public ResponseEntity<?> getBoss(@PathVariable("id") String id) {
//		System.out.println("getBoss Controller");
//		try {
//
//			System.out.println(id);
//			Boss bossInfo = bossService.getBoss(id);
//
//			// 해당 사장님의 입찰 목록
//			List<TenderInfo> list = bossInfo.getDiningList();
//
//			// 입찰 한 dining 중 상세 정보 가져오기
//			List<TenderInfo> tenderList = new ArrayList<>();
//
//			int size = list.size();
//			
//			for (int i = 0; i < size; i++) {
//				// 같은 dining 에 여러번 입찰 가능해서 list임
//				List<Dining> temp = diningService.getDiningByBoss(id);
//
//				// 같은 dining 에서 list 가져와서 그만큼 건너뛰어야함 
//				int cal = temp.size() - 1;
//				i += cal;
//				
//				tenderList.addAll(temp);
//			}
//
//			bossInfo.setDiningList(tenderList);
//
//			return new ResponseEntity<Boss>(bossInfo, HttpStatus.OK);
//		} catch (Exception e) {
//			System.out.println(e);
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//
//	}

	@PostMapping("/delete")
	@ApiOperation(value = "사장님 탈퇴")
	public ResponseEntity<HashMap<String, Object>> deleteBoss(@RequestBody Boss boss) {
		System.out.println("delete Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			bossService.deleteBoss(boss);
			map.put("boss", "removed");

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{addr}/{status}")
	@ApiOperation(value = "주소 경매 리스트 확인")
	public ResponseEntity<List<Dining>> getAllAuction(@PathVariable("addr") String addr,
			@PathVariable("status") int status) {
		System.out.println("getAllAuction Controller");

		try {
			HashMap<String, Object> map = new HashMap<>();

			List<Dining> list = bossService.getAllAuction(addr, status);

			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getDnLocation() + " " + list.get(i).getDnName());
			}

			return new ResponseEntity<List<Dining>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/tender")
	@ApiOperation(value = "사장님 입찰")
	public ResponseEntity<?> tender(@RequestBody TenderInfo tenderInfo) {
		System.out.println("tender Controller");

		try {
			Dining dining = bossService.tender(tenderInfo.getDnID(), tenderInfo.getBoID(), tenderInfo.getPrice(),
					tenderInfo.getMemo());

			return new ResponseEntity<Dining>(dining, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

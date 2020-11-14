package com.mimi.Controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Party;
import com.mimi.Dto.Store;
import com.mimi.Service.StoreService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	StoreService storeService;

	
	
	@GetMapping()
	@ApiOperation(value = "store 전체 리스트 페이징 출력")
	public ResponseEntity<?> getAllStoreList(@RequestParam int pageno) {
		Pageable request = PageRequest.of(pageno, 16);
		Page<Store> list = storeService.findAllPaging(request);
		return new ResponseEntity<Page<Store>>(list, HttpStatus.OK);
	}
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "id로 가게 가져오기")
	public ResponseEntity<?> getStore(@PathVariable("id") String id) {
		System.out.println("getStore Controller");
		try {
			Store store = storeService.getStore(id);

			return new ResponseEntity<Store>(store, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	

	@GetMapping("surbey")
	public ResponseEntity<?> surbey() {
		List<Store> list = storeService.findAll();

		List<Store> ret = new LinkedList<Store>();
		ret.add(list.get(0));
		ret.add(list.get(8));
		ret.add(list.get(13));
		ret.add(list.get(21));
		ret.add(list.get(23));
		return new ResponseEntity<List<Store>>(ret, HttpStatus.OK);

	}

}

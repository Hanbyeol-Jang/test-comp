package com.mimi.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Si;
import com.mimi.Dto.Sigun;
import com.mimi.Dto.Sigundong;
import com.mimi.Service.CityService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/city")
public class CityController {

	@Autowired
	private CityService cityService;

	@GetMapping
	@ApiOperation(value = "시 list")
	public ResponseEntity<?> siList() {
		System.out.println("si list Controller");

		try {
			ArrayList<String> list = cityService.siList();

			return new ResponseEntity<ArrayList<String>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/{si}")
	@ApiOperation(value = "시군 list")
	public ResponseEntity<?> sigunList(@PathVariable String si) {
		System.out.println("sigun list Controller");

		try {
			ArrayList<String> list = cityService.sigunList(si);

			return new ResponseEntity<ArrayList<String>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/{si}/{gun}")
	@ApiOperation(value = "시군동 list")
	public ResponseEntity<?> sigundongList(@PathVariable String si, @PathVariable String gun) {
		System.out.println("sigundong list Controller");

		try {
			ArrayList<String> list = cityService.sigundongList(si, gun);

			return new ResponseEntity<ArrayList<String>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
}

package com.mimi.Dto;

 import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "party")
public class PartyResponse {
	@Id
	private String id;

	private List<User> userList;
	private String ptName;
	private String promiseLocation;
	
	// 약속 정보
	private Store promiseStore;
	private String promiseTime;
}

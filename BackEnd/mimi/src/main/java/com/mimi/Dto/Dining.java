package com.mimi.Dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "dining")
public class Dining {

	@Id
	private String id;

	private int dnStatus;
	private String dnName;
	private String dnLocation;
	private LocalDate dnDate;
	private int dnPeople;
	private int dnPrice;
	private List<String> dnMenu;
	private User user;
//	private String uiID;
//	private String uiName;
//	private String uiEmail;
//	private String uiProfile;
//	private String uiThumb;
//	private String uiToken;
	private ArrayList<TenderInfo> storeList;
	private String soldID;

}

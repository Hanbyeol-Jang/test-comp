package com.mimi.Service;

import java.util.ArrayList;

public interface CityService {

	// 시
	public ArrayList<String> siList();

	// 시군
	public ArrayList<String> sigunList(String si);

	// 시군동
	public ArrayList<String> sigundongList(String si, String sigun);

}

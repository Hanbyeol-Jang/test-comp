package com.mimi.Service;

import java.util.List;

import com.mimi.Dto.Dining;

public interface DiningService {

	// 경매 등록
	public Dining upload(Dining dining);

	// 경매 취소
	public void remove(Dining dining);

	// 경매 확인
	public Dining getDining(String id);

	// 사장님 자신이 입찰한 경매정보 확인
	public List<Dining> getDiningByBoss(String boID);

	// 가입된 회식 목록
	public List<String> diningList(String userId);

}

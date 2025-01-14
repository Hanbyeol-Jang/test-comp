package com.mimi.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.PartyDao;
import com.mimi.Dao.UserDao;
import com.mimi.Dto.Party;
import com.mimi.Dto.PartyRequest;
import com.mimi.Dto.User;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	private PartyDao partyDao;

	@Autowired
	private UserDao userDao;

	@Override
	public Party createParty(PartyRequest partyReq) {

		Party party = new Party();

		List<User> list = new ArrayList<>();
		list.add(userDao.findById(partyReq.getUserID()).get());

		party.setUserList(list);
		party.setPtName(partyReq.getPtName());
		party.setPromiseLocation(partyReq.getPromiseLocation());
		return partyDao.save(party);
	}
	
	@Override
	public Party joinParty(String userId, String partyId) {
		// 모임에 초대 됨
		Party party = partyDao.findById(partyId).get();

		List<User> list = party.getUserList();
		list.add(userDao.findById(userId).get());

		party.setUserList(list);

		partyDao.save(party);

		// user 에 모임 추가
		User user = userDao.findById(userId).get();

		List<String> userList = user.getPartyList();
		userList.add(partyId);

		user.setPartyList(userList);

		userDao.save(user);
		return party; 
	}

	@Override
	public List<String> partyList(String userId) {
		User user = userDao.findById(userId).get();

		List<String> list = user.getPartyList();

		return list;
	}

	@Override
	public void deleteParty(Party party) {
		partyDao.deleteById(party.getId());
	}

	@Override
	public Party save(Party party) {
		return partyDao.save(party);
	}

	@Override
	public Party getParty(String id) {
		return partyDao.findById(id).get();
	}

}

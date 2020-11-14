package com.mimi.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.SiDao;
import com.mimi.Dao.SigunDao;
import com.mimi.Dao.SigundongDao;
import com.mimi.Dto.Si;
import com.mimi.Dto.Sigun;
import com.mimi.Dto.Sigundong;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private SiDao siDao;

	@Autowired
	private SigunDao sigunDao;

	@Autowired
	private SigundongDao sigundongDao;

	@Override
	public ArrayList<String> siList() {
		List<Si> list = siDao.findAll();
		ArrayList<String> si = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			si.add(list.get(i).getSi());
		}

		return si;
	}

	@Override
	public ArrayList<String> sigunList(String si) {
		List<Sigun> list = sigunDao.findBySi(si);
		ArrayList<String> sigun = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			sigun.add(list.get(i).getGun());
		}

		return sigun;
	}

	@Override
	public ArrayList<String> sigundongList(String si, String gun) {
		List<Sigundong> list = sigundongDao.findBySiAndGun(si, gun);
		ArrayList<String> sigundong = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			sigundong.add(list.get(i).getDong());
		}

		return sigundong;
	}

}

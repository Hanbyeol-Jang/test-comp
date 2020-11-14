package com.mimi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.RecommandDao;
import com.mimi.Dao.ReviewdataDao;
import com.mimi.Dto.Recommand;
import com.mimi.Dto.RecommandRequest.Survey;
import com.mimi.Dto.Reviewdata;

@Service
public class RecommandService {

	@Autowired
	private ReviewdataDao reviewdataDao;

	@Autowired
	private RecommandDao recommandDao;

	public void survey(Survey[] list) {
		int index = (int) reviewdataDao.count();
		for (int i = 0; i < list.length; i++) {
			Reviewdata temp = new Reviewdata();
			temp.setId(String.valueOf(index+1+i));
			temp.setRating(list[i].getRating());
			temp.setResId(list[i].getRid());
			temp.setUserId("" + list[i].getUid());
			reviewdataDao.save(temp);
		}
	}

	public Recommand save(Recommand recommand) {
		recommandDao.save(recommand);
		return recommand;
	}

	public List<Recommand> findAll() {
		return recommandDao.findAll();
	}

	public List<Recommand> recom(String name, String address) {
//		address = "%"+address+"%";
		return recommandDao.findByUidAndAddressLikeOrderByRid(name, address);
	}

}

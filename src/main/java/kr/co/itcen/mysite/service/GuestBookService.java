package kr.co.itcen.mysite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.co.itcen.mysite.repository.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Service
public class GuestBookService {
	
	@Autowired
	private GuestbookDao guestbookDao;
	
	//전체 리스트를 가지고 오는 method
	public List<GuestbookVo> getList() {
		
		List<GuestbookVo> result = new ArrayList<GuestbookVo>();
		result = guestbookDao.getList(); 
		
		return result;	
	}
	
	//방명록 입력하는 Service
	public void insertAddList(GuestbookVo vo) {
		guestbookDao.insert(vo);
	}
	
	//방명록 삭제하는 Service
	public void deleteList(GuestbookVo vo) {
		guestbookDao.delete(vo);
	}
	
	
	
	

}

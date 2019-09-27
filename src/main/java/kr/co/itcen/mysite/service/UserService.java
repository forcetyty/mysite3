package kr.co.itcen.mysite.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.co.itcen.mysite.repository.UserDao;
import kr.co.itcen.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void join(UserVo vo) {
		// TODO Auto-generated method stub
		userDao.insert(vo);
	}

	public UserVo getUser(UserVo vo) {
		// TODO Auto-generated method stub
		return userDao.get(vo);

	}

	// 회원정보를 가져오는 기능
	public UserVo selectList(Long memberNo) {
		return userDao.selectUpdate(memberNo);
	}

	// 회원정보수정
	public void updateList(UserVo vo) {
		userDao.updateUser(vo);
	}

}

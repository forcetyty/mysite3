package kr.co.itcen.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}

	// 오버로드
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo vo) {
		userService.join(vo);

		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserVo vo, HttpSession session, Model model) {
		UserVo userVo = userService.getUser(vo);

		if (userVo == null) {
			model.addAttribute("result", "fail");
			return "user/loing";
		}

		// 로그인 처리
		session.setAttribute("authUser", userVo);
		return "redirect:/";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		// 접근 제어
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser != null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
		return "redirect:/";
	}

	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateForm(UserVo vo, HttpSession session) {
		System.out.println("회원정보 수정");
		
		// 접근 제어
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		userService.selectList(authUser.getNo());
				
		return "user/update";
	}
	
//	@RequestMapping(value = "/updateProcess")
//	public String update(@ModelAttribute UserVo vo) {
//		userService.updateList(vo);
//		
//		return "user/update" + vo.getNo();
//	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handlerException() {
//		
//		return "error/exception";
//	}

}

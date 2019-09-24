package kr.co.itcen.mysite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.GuestBookService;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestBookService;
	
	@RequestMapping(value = "")
	public String guestBookList(Model model) {
		System.out.println("list 호출");
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		list = guestBookService.getList();
		
		model.addAttribute("list", list);
		
		return "guestbook/list";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addList(GuestbookVo vo) {
		System.out.println("add 호출");
		guestBookService.insertAddList(vo);
		
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{no}", method = RequestMethod.GET)
	public String deleteList(@PathVariable("no") Long no, Model model) {
		System.out.println("deleteForm 호출");
			
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{no}", method = RequestMethod.POST)
	public String deleteList(@ModelAttribute GuestbookVo vo) {
		System.out.println("delete 호출");
		
		System.out.println(vo.getNo() + vo.getPassword()+vo.getContents()+vo.getName());
		guestBookService.deleteList(vo);
		
		return "redirect:/guestbook";	
	}
	

	

}

package kr.co.itcen.mysite.controller;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	
	@RequestMapping(value = { "", "list" }, method = RequestMethod.GET)
	public String getBoardlist(@RequestParam(value="page",required=false, defaultValue="1") int page, Model model) {
		
		System.out.println("Board List 출력");
		//Optional page
		System.out.println(page);

		//if (page == null) {
			//page.
			//int pageNum = Integer.parseInt(page);
			//pageNum = 1;
			//System.out.println(pageNum);
		//}
		
		  System.out.println(page);
		  
		  int firstBoardlist = 1;
		  int maxBoardlist = 5; // 최종 게시판 리스트 int firstBoardlist = 1; // 시작 게시판!!!
		  
		  firstBoardlist = page;
		  
		  int pageNum = ((firstBoardlist - 1) / 5) * 5;
		  
		  System.out.println(pageNum);
		  
		  model.addAttribute(pageNum);
		  
		 model.addAttribute("list", boardService.getList((firstBoardlist - 1) *
		 maxBoardlist, maxBoardlist));
		 
		return "board/list";
	}

}

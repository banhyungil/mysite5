package kr.co.itcen.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.security.AuthUser;
import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@RequestMapping("board/")
@Controller
public class BoardController {
	//DI를 위한 Annotation

	@Autowired
	private BoardService boardService;

	@RequestMapping(value="insert", method=RequestMethod.GET)
	public String insert(@RequestParam(value="parentBoardNo", defaultValue="0") Long parentBoardNo, Model model) {
		model.addAttribute("parentBoardNo",parentBoardNo);
		return "board/insert";
	}
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@AuthUser UserVo authUser, BoardVo vo, 
			@RequestParam(value="parentBoardNo", defaultValue="0") Long parentBoardNo) {
		vo.setUserNo(authUser.getNo());
		
		System.out.println(vo + "parentBoardNo : " + parentBoardNo);
		if(parentBoardNo == 0)
			boardService.writeBoard(vo);
		else
			boardService.writeReplyBoard(vo, parentBoardNo);
		
		return "redirect:/board/list";
	}
	@RequestMapping("contentsform")
	public String contentsform(
			@RequestParam(value="no", required=true) Long no, Model model ) {
		
		BoardVo vo = boardService.readBoard(no);
		
		model.addAttribute("vo", vo);	
		return "board/contentsform";
	}
	@RequestMapping(value="update", method=RequestMethod.GET)
	public String update(@RequestParam(value="no", required=true) Long no, Model model) {
		BoardVo vo = boardService.get(no);
		model.addAttribute("vo", vo);

		return "board/update";
	}
	@RequestMapping(value="update", method=RequestMethod.POST)
	public String update(BoardVo vo, Model model) {
		vo = boardService.updateContents(vo);
		System.out.println("BoardControl : " + vo);
		model.addAttribute("vo", vo);

		return "board/contentsform";
	}
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(@ModelAttribute @RequestParam(value="no", required=true) Long no) {
		boardService.delete(no);
		return "redirect:/board/list";
	}
	@RequestMapping("list")
	public String list(@RequestParam(value="keyword", defaultValue="") String keyword, 
			@RequestParam(value="currentPage", defaultValue="1") Integer currentPage, 
			@RequestParam(value="currentPageBlock", defaultValue="1") Integer currentPageBlock,
			Model model) {
		
		Map map = boardService.getListMap(keyword, currentPage, currentPageBlock);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("currentPageBlock", map.get("currentPageBlock"));
		model.addAttribute("firstPage", map.get("firstPage"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("pageRows", map.get("pageRows"));
		model.addAttribute("keyword", keyword);
		return "board/list";

	}
}

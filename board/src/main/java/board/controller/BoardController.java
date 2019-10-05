package board.controller;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception {
		ModelAndView mv = new ModelAndView();
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list",list);
		mv.setViewName("/board/boardList");
		
		return mv;
	}
	
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}
	
	@RequestMapping("/board/insertBoard.do")
	public void insertBoard(BoardDto dto, HttpServletRequest request, HttpServletResponse response) throws Exception{
		dto.setCreatorId("admin");
		boardService.insertBoard(dto);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/board/openBoardList.do");
		dispatcher.forward(request, response);
		
		//return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping(value = "/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam("board_idx") int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board",board);
		mv.setViewName("/board/boardDetail");
		
		return mv;
	}
	
	@RequestMapping(value = "/board/updateBoard.do")
	public void updateBoard(BoardDto dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
		dto.setUpdateId("admin");
		boardService.updateBoard(dto);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/board/openBoardList.do");
		dispatcher.forward(request, response);
	}
	
	@RequestMapping(value = "/board/deleteBoard.do")
	public void deleteBoard(BoardDto dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
		dto.setUpdateId("admin");
		boardService.deleteBoard(dto);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/board/openBoardList.do");
		dispatcher.forward(request, response);
	}
}

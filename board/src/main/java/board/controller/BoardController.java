package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.io.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;

@Controller
public class BoardController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception {
		
		log.debug("openBoardList");
		
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
	public void insertBoard(BoardDto dto, HttpServletRequest request, HttpServletResponse response
			, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		
		dto.setCreatorId("admin");
		boardService.insertBoard(dto,multipartHttpServletRequest);
		
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
	
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}

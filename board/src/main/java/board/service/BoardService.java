package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

public interface BoardService {
	
	public List<BoardDto> selectBoardList() throws Exception;
	public void insertBoard(BoardDto dto ,MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	public BoardDto selectBoardDetail(int boardIdx) throws Exception;
	public void updateBoard(BoardDto dto) throws Exception;
	public void deleteBoard(BoardDto dto) throws Exception;
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
}

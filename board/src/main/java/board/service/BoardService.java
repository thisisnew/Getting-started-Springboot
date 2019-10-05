package board.service;

import java.util.List;

import board.dto.BoardDto;

public interface BoardService {
	
	public List<BoardDto> selectBoardList() throws Exception;
	public void insertBoard(BoardDto dto) throws Exception;
	public BoardDto selectBoardDetail(int boardIdx) throws Exception;
	public void updateBoard(BoardDto dto) throws Exception;
	public void deleteBoard(BoardDto dto) throws Exception;
}

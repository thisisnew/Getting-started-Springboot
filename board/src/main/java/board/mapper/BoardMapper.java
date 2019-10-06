package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto dto) throws Exception;
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	void updateHitCnt(int boardIdx) throws Exception;
	void updateBoard(BoardDto dto) throws Exception;
	void deleteBoard(BoardDto dto) throws Exception;
	void insertBoardFileList(List<BoardFileDto> list)throws Exception;
	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
	BoardFileDto selectBoardFileInformation(@Param("idx") int idx,
											@Param("boardIdx") int boardIdx) throws Exception;
}

package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface JpaBoardService {
	List<BoardEntity> selectBoardList() throws Exception;
	void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	BoardEntity selectBoardDetail(int boardIdx) throws  Exception;
	void deleteboard(int boardIdx) throws Exception;
	BoardFileEntity selectBoardFIleInformation(int boardIdx,int idx) throws Exception;
}

package board.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto dto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.insertBoard(dto);
		List<BoardFileDto> list = fileUtils.parseFileInfo(dto.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list)==false) {
			boardMapper.insertBoardFileList(list);
		}
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		
		BoardDto boardDto = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> list = boardMapper.selectBoardFileList(boardIdx);
		boardDto.setList(list);
		
		boardMapper.updateHitCnt(boardIdx);
		
		return boardDto;
	}

	@Override
	public void updateBoard(BoardDto dto) throws Exception {
		boardMapper.updateBoard(dto);
	}

	@Override
	public void deleteBoard(BoardDto dto) throws Exception {
		boardMapper.deleteBoard(dto);
	}

	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}

}

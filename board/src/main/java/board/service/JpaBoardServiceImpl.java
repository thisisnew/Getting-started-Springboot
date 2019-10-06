package board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceImpl implements JpaBoardService{
	
	@Autowired
	JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	FileUtils fileUtils;
	
	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}

	@Override
	public void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest multipartHttpServletRequest)
			throws Exception {
		boardEntity.setCreatorId("admin");
		List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false){
			boardEntity.setFileList(list);
		}
		jpaBoardRepository.save(boardEntity);
	}

	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
		Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
		if(optional.isPresent()){
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt() + 1);
			jpaBoardRepository.save(board);
			
			return board;
		}
		else {
			throw new NullPointerException();
		}
	}

	@Override
	public void deleteboard(int boardIdx) throws Exception {
		jpaBoardRepository.deleteById(boardIdx);
		
	}

	@Override
	public BoardFileEntity selectBoardFIleInformation(int boardIdx, int idx) throws Exception {
		BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(boardIdx, idx);
		return boardFile;
	}

}

package board.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoardDto {
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createdDatetime;
	private String updateId;
	private String updatedDatetime;
	private List<BoardFileDto> list;
}

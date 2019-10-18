package kr.co.itcen.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	BoardDao boardDao;

	public Map getListMap(String keyword, int currentPage, int currentPageBlock) {
		List<BoardVo> list = null;
		Map map = new HashMap<String, Object>();
		
		final int pageRows = 5;
		final int pageBlockSize = 5;
		int totalRows, totalPage;
		int firstPage, lastPage;
		int beginRow;
		
		currentPage = currentPage - 1;
		beginRow = currentPage * pageRows;
		
		if(!keyword.isEmpty()) {				//찾기 기능
			totalRows = boardDao.getListCount(keyword);
			list = boardDao.getList(keyword, pageRows, beginRow);
		} else {
			totalRows = boardDao.getListCount();
			list = boardDao.getList(pageRows, beginRow);
		}

		totalPage = (totalRows / pageRows) + 1;
		currentPageBlock = (currentPage / pageBlockSize);
		/* 현재 페이지  Block의 first page, last page 계산 */
		firstPage = currentPageBlock * pageBlockSize + 1;
		lastPage = currentPageBlock * pageBlockSize + pageBlockSize;
		
		if(lastPage > totalPage)
			lastPage = totalPage;
		
		map.put("list", list);
		map.put("currentPageBlock", currentPageBlock);
		map.put("firstPage", firstPage);
		map.put("lastPage", lastPage);
		map.put("pageRows", pageRows);

		return map;
	}

	public BoardVo readBoard(Long no) {
		BoardVo vo = boardDao.get(no);

		boardDao.updateHit(no);
		return vo;
	}

	public BoardVo updateContents(BoardVo vo) {
		boardDao.update(vo);
		
		System.out.println("update : " + vo);
		//update된 BoardVo를 받는다.
		return boardDao.get(vo.getNo());
	}
	
	public BoardVo get(Long no) {
		return boardDao.get(no);
	}

	public void writeBoard(BoardVo vo) {
		boardDao.insert(vo);
	}
	
	public void writeReplyBoard(BoardVo vo,Long parentBoardNo) {
		BoardVo parentVo = boardDao.get(parentBoardNo);
		vo.setgNo(parentVo.getgNo());
		vo.setoNo(parentVo.getoNo());
		vo.setDepth(parentVo.getDepth());
		
		boardDao.updateBoards(parentVo.getgNo(), parentVo.getoNo());
		
		boardDao.insertReply(vo);
	}

	public Boolean delete(Long no) {
		return boardDao.delete(no);
	}

}

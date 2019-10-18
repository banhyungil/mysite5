package kr.co.itcen.mysite.repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	SqlSession sqlSession;

	public List<BoardVo> getList(Integer pageRows, Integer beginRow){		
		Map map = new HashMap<String, Object>();
		map.put("pageRows", pageRows);
		map.put("beginRow", beginRow);

		return sqlSession.selectList("board.getList", map);
	}
	
	public List<BoardVo> getList(String keyword, int pageRows, int beginRow) {
		keyword = "%" + keyword + "%";		
		
		Map map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("pageRows", pageRows);
		map.put("beginRow", beginRow);

		return sqlSession.selectList("board.getListWithKeyword", map);
	}
	
	public Integer getListCount() {					
		return sqlSession.selectOne("board.getListCount");
	}
	
	public int getListCount(String keyword) {
		keyword = "%" + keyword + "%";

		return sqlSession.selectOne("board.getListCount",keyword);
	}
	
	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		
		return (count == 1);
	}
	/*
	 * 답글 작성시 insert 구문
	 */
	public Boolean insertReply(BoardVo vo) {
		int count = sqlSession.insert("board.insertReply", vo);
		
		return (count == 1);
	}

	public BoardVo get(Long no) {
		return sqlSession.selectOne("board.get", no);
	}

	public Boolean update(BoardVo vo) {		
		System.out.println(vo);
		int count = sqlSession.update("board.update", vo);
		
		return (count == 1);
	}
	
	public Boolean updateBoards(int gNo, int oNo) {
		Map map = new HashMap<String, Integer>();
		map.put("gNo", gNo);
		map.put("oNo", oNo);
	
		int count = sqlSession.update("board.updateByReply", map);
	
		return (count == 1);
	}
	
	public Boolean updateHit(Long no) {
		int count = sqlSession.update("board.updateHit", no);
		
		return (count == 1);
	}
	
	public Boolean delete(Long no) {
		int count = sqlSession.delete("board.delete", no);
		
		return (count == 1);
	}

	

	

	


}

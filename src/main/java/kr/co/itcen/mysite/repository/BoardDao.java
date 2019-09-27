package kr.co.itcen.mysite.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardCountVo;
import kr.co.itcen.mysite.vo.BoardSerchVo;
import kr.co.itcen.mysite.vo.BoardUserListVo;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private	DataSource dataSource;
	
	// 전체 게시글의 수를 가져오는 쿼리
	public BoardCountVo countList() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardCountVo vo = new BoardCountVo();
		
		try {
			connection = dataSource.getConnection();
		
			String sql = "select count(*) as num from board where status = 1";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			int num = rs.getInt(1);
			
			vo.setCountRow(num);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
		// 전체 게시글의 수를 가져오는 쿼리
		public BoardCountVo countList(String kwd) {
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			BoardCountVo vo = new BoardCountVo();
			
			try {
				connection = dataSource.getConnection();
			
				String sql = "select count(*) as num from board where status = 1 and like (board = ? or title = ?)";
				pstmt = connection.prepareStatement(sql);
				
				//Like 검색어 핵심요소
				pstmt.setString(1, "%" + kwd + "%");
				pstmt.setString(2, "%" + kwd + "%");

				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				int num = rs.getInt(1);
				
				vo.setCountRow(num);
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return vo;
		}
	
	// title과 contents로 검색가능하게 하는 Dao
	public List<BoardSerchVo> serchList(String kwd) {
		
		List<BoardSerchVo> result = new ArrayList<BoardSerchVo>();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			
			//아래 쿼리를 통해서 리스트에 답글과 댓글을 표시
			String sql = "select b.no, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.g_no, b.o_no, b.depth from user as u, board as b where u.no = b.user_no and b.status = 1  and ( b.title like ? or b.contents like ? ) order by b.g_no desc, b.o_no asc";
			pstmt = connection.prepareStatement(sql);
			
			//Like 검색어 핵심요소
			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setString(2, "%" + kwd + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {

				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				Long hit = rs.getLong(4);
				String date_format = rs.getString(5);
				Long g_no = rs.getLong(6);
				Long o_no = rs.getLong(7);
				Long depth = rs.getLong(8);

				BoardSerchVo vo = new BoardSerchVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setReg_date(date_format);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
		
	}

	// 게시글에 대한 삭제를 처리해주는 Dao
	public void deleteUpdate(Long vo) {
		Connection connection = null; // 연결객체
		PreparedStatement pstmt = null; // 운반객체

		try {
			connection = dataSource.getConnection();

			// 삭제되는 게식글에 대한 항목
			String sql = "update board set status = false where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, vo);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// Hit Update 처리해주는 Dao
	public void hitUpdate(Long vo) {
		Connection connection = null; // 연결객체
		PreparedStatement pstmt = null; // 운반객체

		try {
			connection = dataSource.getConnection();
			// 업데이트 되는 항목 hit
			String sql = "update board set hit = hit + 1 where no = ?";

			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, vo); // 게시판 글에 대한 업데이트 처리

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// View에서 Modify에 들어간후 Modify에서 수정을 했을때의 동작
	// Update를 동작하게 하는 화면!!!
	public void updateModify(BoardVo vo) {

		// Update해주어 되는 값
		// 제목 //내용 //등록일자

		// 데이터베이스와 연결해주는 기능
		Connection connection = null; // 연결객체
		PreparedStatement pstmt = null; // 운반객체

		try {
			connection = dataSource.getConnection();
			// 업데이트 되는 항목 title / contents / reg_date
			String sql = "update board set title = ?, contents = ?, reg_date = now() where no = ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// View에 선택한 게시글을 표시해주는 Dao Method
	public BoardViewVo selectView(Long no) {
		BoardViewVo result = new BoardViewVo();

		// List<BoardUserListVo> result = new ArrayList<BoardUserListVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();

			/// 제목, 글쓴이, 메일, 등록일, 조회수, 내용
			// 이 Dao를 통해서 그룹번호, 답글순서, 깊이까지 가져온다
			String sql = "select b.no, b.title, u.name , u.email, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.hit, b.contents, b.user_no, b.g_no, b.o_no, b.depth from user as u, board as b where b.user_no = u.no and b.no = ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				Long num = rs.getLong(1); // 글 번호
				String title = rs.getString(2); // 타이틀
				String name = rs.getString(3); // 이름
				String email = rs.getString(4); // 이메일
				String date_format = rs.getString(5); // 날짜
				Long hit = rs.getLong(6); // 조회수
				String contents = rs.getString(7); // 글 내용
				Long user_no = rs.getLong(8); // 유저 번호
				Long g_no = rs.getLong(9);	//그룹번호
				Long o_no = rs.getLong(10); //답글 순서
				Long depth = rs.getLong(11);	//깊이

				BoardViewVo vo = new BoardViewVo();

				vo.setNo(num);
				vo.setTitle(title);
				vo.setName(name);
				vo.setEmail(email);
				vo.setReg_date(date_format);
				vo.setHit(hit);
				vo.setContents(contents);
				vo.setUser_no(user_no);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);

				result = vo;
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	// MyBatis 적용완료!!!
	// 게시판에 글을 출력해주는 Dao
	public List<BoardUserListVo> getList(int start, int end) {
		Map<String, Integer> serch = new HashMap<String, Integer>();
		
		serch.put("startNum", start);
		serch.put("endNum", end);
		
		List<BoardUserListVo> result = sqlSession.selectList("board.getList",serch);
		return result;
	}

	// 원 게시글을 등록하는 Dao
	// 답글과 댓글 구현 Dao는 Reply Dao에 구현되어 있음
	public Boolean insertBoardDao(BoardVo vo) {
		Boolean result = false;
		int count = 0;
		
		count = sqlSession.insert("board.insertBoard", vo);
		
		//if(count == 1)
		
		return result;
		//sqlSession.insert("board.insertBoard", vo);
		
//		Boolean result = false;
//
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//		
//			connection = dataSource.getConnection();
//
//			String sql = "insert into board values(null, ?, ?, 0, now(), (select ifnull(max(bo.g_no)+1, 1) from board as bo), 0, 0, ?, ?)";
//
//			pstmt = connection.prepareStatement(sql);
//
//			pstmt.setString(1, bvo.getTitle());
//			pstmt.setString(2, bvo.getContents());
//			pstmt.setLong(3, bvo.getUser_no());
//			pstmt.setBoolean(4, true);
//
//			int count = pstmt.executeUpdate();
//			result = (count == 1);
//
//			stmt = connection.createStatement();
//
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (stmt != null) {
//					stmt.close();
//				}
//
//				if (pstmt != null) {
//					pstmt.close();
//				}
//
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return result;
	}

//	// DataBase와 연결시키는 객체
//	private Connection getConnection() throws SQLException {
//		Connection connection = null;
//
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//
//			String url = "jdbc:mariadb://192.168.1.81:3306/webdb?characterEncoding=utf8";
//			connection = DriverManager.getConnection(url, "webdb", "webdb");
//
//		} catch (ClassNotFoundException e) {
//			System.out.println("Fail to Loading Driver:" + e);
//		}
//
//		return connection;
//	}

}

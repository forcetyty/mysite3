package kr.co.itcen.mysite.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.itcen.mysite.vo.BoardRelyVo;


// [고민]
// 1. Vo 객체가 중복된다.
//    객체가 중복된 원인은 객체에 따라서 각기 다른 기능을 해주기 위함이었다
// 2. 중복되는 Vo 객체를 줄이는것이 맞는것일까 아니면, 분리시키는것이 맞는것일까?
//    분리시킨 이유 중 하나는 단일 책임원칙 때문이었다.
public class ReplyDao {

	@Autowired
	private DataSource dataSource;

	// 원글에 대한 답글을 달때
	public boolean replyInsert(BoardRelyVo vo) {

		Boolean result = false;

		Connection connection = null; // 연결객체
		PreparedStatement pstmt = null; // 운반객체

		Statement stmt = null;		  // SQL문을 데이터베이스에 보내기 위한 객체
		ResultSet rs = null;		  // SQL질의에 의해 생성된 테이블을 저장하는 객체

		try {
			connection = dataSource.getConnection();

			// -- no / title / contents / hit / reg_date / g_no / o_no / depth / user_no / status
			String sql = "insert into board values(null, ?, ? , 0, now(), ?, o_no + 1, depth + 1, ?, 1)";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getG_no());
			pstmt.setLong(4, vo.getUser_no());
			
		
			int count = pstmt.executeUpdate();
			result = (count == 1);

			stmt = connection.createStatement();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
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

	// 답글에 답글을 달떄
	public boolean reply2Insert(BoardRelyVo vo) {
		Boolean result = false;

		Connection connection = null;   // 연결객체
		PreparedStatement pstmt = null; // 운반객체 - Query를 묶어서 보내는 기능

		Statement stmt = null;			// SQL문을 데이터베이스에 보내기 위한 객체
		ResultSet rs = null;		    // SQL질의에 의해 생성된 테이블을 저장하는 객체

		try {

			// 데이터베이스와 연결 할 떄 오류가 발생 할 수 있기 때문에
			// 예외처리가 필요
			connection = dataSource.getConnection();

			// -- no / title / contents / hit / reg_date / g_no / o_no / depth / user_no / status
			String sql = "insert into board values(null, ?, ?, hit, now(), ?, (select max(bo.o_no) + 1 from board as bo where bo.g_no = ? ),( select max(boo.depth) + 1 from board as boo where boo.o_no = ?), ?, 1)";

			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getG_no()); //답글을 달려는 원 게시글의 그룹번호
			pstmt.setLong(4, vo.getG_no()); //답글에 댓글을 달려기 위함
			pstmt.setLong(5, vo.getO_no()); //답글과 댓글에 대한 순서를 정해주기 위한 기능
			pstmt.setLong(6, vo.getUser_no()); //게시글을 작성하는 회원 번호
			
			int count = pstmt.executeUpdate();
			result = (count == 1);

			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
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

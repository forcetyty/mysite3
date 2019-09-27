package kr.co.itcen.mysite.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.mysite.vo.UserVo;


@Repository
public class GuestbookDao {
	
		@Autowired
		private SqlSession sqlSession;
		
		@Autowired
		private	DataSource dataSource;
	
		// 데이터베이스에 값을 넣는 기능
		public void updateUser(UserVo vo) {

			Connection connection = null;
			PreparedStatement pstmt = null;

			Statement stmt = null;
			ResultSet rs = null;

			try {

				connection = dataSource.getConnection();

				String sql = "update user set name = ?, password= ?, gender = ? where no = ?";
				pstmt = connection.prepareStatement(sql);

				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getGender());
				pstmt.setLong(4, vo.getNo());

				pstmt.executeUpdate();

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

		}
	
	//데이터베이스에 값을 넣는 기능
	public Boolean insert(GuestbookVo vo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			
			String sql = "insert into guestbook values(null, ?, ?, ?, now());";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			
			
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

	//방명록에 대한 정보를 가져오는 Dao Method
	public List<GuestbookVo> getList() {
		List<GuestbookVo> result = sqlSession.selectList("guestbook.getList");
		return result;
	}
	
	public void delete(GuestbookVo vo) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();

			String sql = "delete from guestbook where no = ? and password = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());

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

//  설정해주었기 때문에 필요가 없다.
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

package kr.co.itcen.mysite.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource dataSource;

	public Boolean update(UserVo vo) {
		Boolean result = false;
		int count = sqlSession.update("user.update",vo);
		
		return count == 1;
	}

	// Update에서 회원의 정보를 가져오는 기능
	public UserVo selectUpdate(Long memberNo) {
		UserVo result = null;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();

			String sql = "select no, name, email, gender, join_date from user where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, memberNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				String join_date = rs.getString(5);

				result = new UserVo();

				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
				result.setJoin_date(join_date);

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

	public Boolean insert(UserVo vo) throws UserDaoException {
		int count = sqlSession.insert("user.insert", vo);
		System.out.println(vo);
		return count == 1;
	}

	public UserVo get(Long no) {
		return sqlSession.selectOne("user.getByNo", no);
		
	}

	public UserVo get(UserVo vo) throws UserDaoException {
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword1", vo);
		return result;
	}

	public UserVo get(String email, String password) throws UserDaoException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);

		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword2", map);
		return result;
	}

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

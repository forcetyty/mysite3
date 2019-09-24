package kr.co.itcen.mysite.vo;

public class BoardVo {

	private Long no; // 게시판 번호
	private String title; // 게시판 제목
	private String contents; // 게시판 내용
	private Long hit; // 게시판 조회수
	private String reg_date; // 게시판 등록일자
	private Long g_no; // 부모
	private Long o_no; // 답글
	private Long depth; // 답글의 깊이
	private Long user_no; // 유저번호
	private Boolean status;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public Long getG_no() {
		return g_no;
	}

	public void setG_no(Long g_no) {
		this.g_no = g_no;
	}

	public Long getO_no() {
		return o_no;
	}

	public void setO_no(Long o_no) {
		this.o_no = o_no;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Long getUser_no() {
		return user_no;
	}

	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}

	// BoardVo 객체

}

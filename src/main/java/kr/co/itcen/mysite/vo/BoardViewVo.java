package kr.co.itcen.mysite.vo;

public class BoardViewVo {
	private Long no;
	private String title;
	private String name;
	private String email;
	private String reg_date;
	private Long hit;
	private String contents;
	private Long user_no;
	private Long g_no;
	private Long o_no;
	private Long depth;

	public Long getUser_no() {
		return user_no;
	}

	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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

}
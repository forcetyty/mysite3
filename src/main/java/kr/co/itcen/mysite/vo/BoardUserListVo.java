package kr.co.itcen.mysite.vo;

//번호
//제목
//글쓴이
//조회수
//작성일
/**
 * @author BIT
 *
 */
public class BoardUserListVo {
	private Long no;
	private String title;
	private String name;
	private Long hit;
	private String reg_date;
	private Long g_no;
	private Long o_no;
	private Long depth;
	private Boolean status;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}

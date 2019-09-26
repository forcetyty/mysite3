<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Board</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	function deleteEvent() {
		return confirm("글을 삭제하시겠습니까?")
	}
</script>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form"
					action="${pageContext.servletContext.contextPath }/board?a=serach"
					method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>

				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>글삭제</th>
						<th>&nbsp;</th>

					</tr>
					<c:set var="count" value='${fn:length(list) }' />

					<c:forEach items='${list }' var='vo' varStatus='status'>
						<tr>
							<td>${count - status.index }</td>

							<td style="padding-left:${10 * vo.depth}px"><c:if
									test="${vo.depth != 0}">
									<img
										src="${pageContext.servletContext.contextPath }/assets/images/reply.png"
										style='padding-left:${10 * vo.depth}px' />
								</c:if> <a
								href="${pageContext.servletContext.contextPath }/board?a=view&no=${vo.no }">
									${vo.title }</a></td>

							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<td><a
								href="${pageContext.servletContext.contextPath }/board?a=delete&no=${vo.no }"
								class="del" onclick="return deleteEvent();">삭제</a></td>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:if test="${pageNum > 1 }">
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${pageNum}">◀</a></li>
					</c:if>
						<c:forEach begin='1' end='5' step='1' var='i'>
							<c:choose>
								<c:when test="${(page mod 5) eq (i mod 5)}">
								<li class="selected">
										<a href="${pageContext.servletContext.contextPath }/board?page=${pageNum+i }">${pageNum+i }</a>
								</li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath }/board?page=${pageNum + i }"> ${pageNum+i }</a></li>	
								</c:otherwise>
							</c:choose>
						</c:forEach>
	 					<c:if test="${fn:length(list) eq 5}">
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${pageNum+6}">▶</a></li>
						</c:if>
					</ul>
				</div>				

				<!-- pager 추가 -->

				<div class="bottom">
					<a
						href="${pageContext.servletContext.contextPath }/board/writeform"
						id="new-book">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
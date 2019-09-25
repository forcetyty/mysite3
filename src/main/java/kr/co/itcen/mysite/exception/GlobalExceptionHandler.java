package kr.co.itcen.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sun.istack.internal.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
	//AOP를 실행시키는 모듈 - Advice
	
	//기술침투는 Servlet과 Controller에서 발생하는 침투를 막아야 한다.
	
	@ExceptionHandler(UserDaoException.class)
	public void handlerException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception{
		
		//1.로깅
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		System.out.println(errors.toString());
		
		// Logger.eroor(errors.toString());
		
		//2.안내페이지
		request.setAttribute("uri", request.getRequestURI());
		request.setAttribute("exception", errors.toString());
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		
		
	}

}

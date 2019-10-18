package kr.co.itcen.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.itcen.mysite.dto.JSONResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	//SLF4J(Simple Logging Facade)를 통해 Log객체를 받아온다. 구현체는 LogBack을 쓴다
	private static final Log Log = LogFactory.getLog( GlobalExceptionHandler.class );
	
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		
				//1. 로깅
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				Log.error(errors.toString());

				//2. 요청 구분
				// 만약 JSON 요청인 경우는 application/json
				// 만약 html 요청인 경우는 text/html
				// 만약 jpeg 요청인 경우는 image/jpeg
				String accept = request.getHeader("accept");
				//정규식 표현으로 처리한 이유는 AJax통신 일때 브라우저마다 조금씩 다르다.
				//request의 Header는 각 브라우저가 붙여준다.
				if(accept.matches(".*application/json.*")) {
					//3. json 응답
					//json 응답 시
					response.setStatus(HttpServletResponse.SC_OK);
					
					JSONResult jsonResult = JSONResult.fail(errors.toString());
					//Message Converter가 하는일을 직접 수행
					//객체를 Json형식으로 바꾸어준다.
					String result = new ObjectMapper().writeValueAsString(jsonResult);
					
					OutputStream os = response.getOutputStream();
					os.write(result.getBytes("utf-8"));
					os.close();
				} else {
					//3. 안내 페이지
					request.setAttribute("uri", request.getRequestURI());
					request.setAttribute("exception", errors.toString());
					request
						.getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
						.forward(request, response);
				}
	}
}

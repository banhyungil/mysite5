<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<!-- Jquery는 JavaScript Element를 Jquery 객체로 패키징함 -->
<!-- JavaScript는 함수가 객체이다 -->
<!-- $(<function>)에 의미는  Dom 이벤트에서 loaded가 된후에 해당 함수를 호출한다는 뜻이다. -->
<script>
	var f = function() {
		var $btnCheckEmail = $("#btn-check-email");
		$btnCheckEmail.clik(function() {
			var email = $("#input-email").val();
			if (email == "") {
				return;
			}

			// AJAX 통신
			// 객체로 값을 넘긴다.
			$.ajax({
				url : "/mysite3/api/user/checkemail?email=" + email,
				type : "get",
				dataType : "json",
				data : "",
				success : function(response) {
					if (response.result == "fail") {
						console.error(response.message);
						return;
					}

					if (response.data == true) {
						alert("이미 존재하는 메일입니다.");
						$("#input-email").val("");
						$("#input-email").focus();
						return;
					}

					$("#btn-check-email").hide();
					$("#img-checked").show();
				},
				error : function(xhr, error) {
					console.error("error:" + error);
				}
			});
		});
	}

	$(f);
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form 
				modelAttribute="userVo"
				id="join-form" 
				name="joinForm" 
				method="post"
				action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label> 
					<form:input path="name"/>
					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("name") }'>
							<p style="font-weight: bold; color: red; text-align: left; padding: 5px 0 0 0">
								<spring:message code='${errors.getFieldError("name").codes[0] }' text='${errors.getFieldError("name").codes[0] }' />
							</p>
						</c:if>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label> 
					<input id="input-email" name="email" type="text" value=""> 
						<input type="button" id="btn-check-email" value="이메일 중복확인"> 
						<img id="img-checked" style='width: 20px; display: none'
						src='${pageContext.servletContext.contextPath }/assets/images/check.png' />
						
						<p style="font-weight: bold; color: red; text-align: left; padding:5px 0 0 0">
							<form:errors path="email"/>
						</p>
					
					<label class="block-label">패스워드</label> 
					<form:password path="password"/>

					<fieldset>
						<legend>성별</legend>
						<label>여</label> 
						<input type="radio" name="gender" value="female" checked="checked"> 
						<label>남</label> 
						<input type="radio" name="gender" value="male">
					</fieldset>
					
					<form:radiobuttons items='${userVo.genders }' path='gender' />
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
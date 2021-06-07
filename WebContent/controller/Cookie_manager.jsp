<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	Cookie name = new Cookie("name", request.getParameter("name"));
	Cookie cost = new Cookie("cost", request.getParameter("cost"));
	
	name.setMaxAge(60*60*10); 
	cost.setMaxAge(60*60*10);
	
	// Add both the cookies in the response header.
	response.addCookie(name);
	response.addCookie(cost);
	
	Cookie cookie = null;
	Cookie[] cookies = null;
	
	cookies=request.getCookies();
%>
<html>
<head>
<meta charset="UTF-8">
<title>Adding to the cart...</title>
</head>
<body>
	Hello world<br>
   <%
   if( cookies != null ) {
       out.println("<h2> Found Cookies Name and Value</h2>");
       
       for (int i = 0; i < cookies.length; i++) {
          cookie = cookies[i];
          out.print("Name : " + cookie.getName( ) + ",  ");
          out.print("Value: " + cookie.getValue( )+" <br/>");
       }
    } else {
       out.println("<h2>No cookies founds</h2>");
    }
	%>
</body>
</html>
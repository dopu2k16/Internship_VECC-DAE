<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HOME PAGE</title>
</head>

<body text="red" background="http://www.hdwallpapers.in/download/snow_mountains_windows_10-1920x1080.jpg"  >

<h1 align="center" ><u>Welcome !<% String str=session.getAttribute("email").toString();%>
                                                   <%=str.toUpperCase()%> to your Home Page</u></h1>
<h2 align="center"> <a href="payslip.jsp"><u>Click to generate your PaySlip</u></a></h2>

</body>
</html>
<%@page import="model.entities.Comment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.entities.Notification"%>
<%@page import="model.entities.UserScheduledLine"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
 Notification n =(Notification) request.getAttribute("notification"); 
 ArrayList<Comment> c = (ArrayList<Comment>) request.getAttribute("comments");
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>	Notification </title>
</head>
<body>


 <h1><% out.print(n.getNotificationText()); %></h1>
 
 
 <% out.println(n.getLine().toString()); %>
 <br/>
 Comments:
 <br/>
 <%
if(c!=null)
  for (Comment p: c) {   
	
	  out.println(p.getCommentText());%>
    <br/>
<%}%>
</body>
</html>
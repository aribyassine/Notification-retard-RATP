<%@page import="model.entities.Comment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.entities.Notification"%>
<%@page import="model.entities.UserScheduledLine"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Notification n = (Notification) request.getAttribute("notification");
	ArrayList<Comment> c = (ArrayList<Comment>) request.getAttribute("comments");
%>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"
	integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
<script type="text/javascript">
	function func() {
		event.preventDefault();

		var comment = document.getElementById("form").elements[1].value
		var notification = document.getElementById("form").elements[0].value

		$.ajax({
			type : "POST",
			url : "comment",
			data : {
				"comment" : comment,
				"notification" : notification
			},
			success : function(msg) {
				console.log("succes"),	$("#head  ul").append('<li>'+comment+'</li>');
			}
		});
		
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Notification</title>
</head>
<body>


	<h1>
		<%
			out.print(n.getNotificationText());
		%>
	</h1>


	<%
		out.println(n.getLine().toString());
	%>
	<br /> Comments:
	<div id="head">
	<ul>
		<%
			if (c != null)
				for (Comment p : c) {
		%>
		<li>
			<%	out.println(p);%>
			
		</li>


		<%
			}
			//	<iframe name="votar" style="display: none;"></iframe>target="votar"
		%>

	</ul>
	</div>
	


	<form action='' method='POST' onsubmit="func()" id="form">
		<input type="hidden" name="notification" id="n"
			value="<%out.print(n.getNotificationId());%>"> <input
			type='text' name='comment' id="c" /> <input type='submit'
			name='send' />




	</form>



</body>
</html>
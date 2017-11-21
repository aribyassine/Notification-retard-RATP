<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page import="model.entities.Notification" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
  List<Notification> nts = (List<Notification>) request.getAttribute("notifications");
%>
<html>
<head>
  <%@ include file="parts/meta.jsp" %>
  <title>Notification</title>
  <%@ include file="parts/head.jsp" %>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <a class="navbar-brand" href="/">Notifieur</a>
    <ul class="nav navbar-nav navbar-right">
      <li>
        <a href="/">Accueil</a>
      </li>
      <li>
        <a href="/usernotifications">Notifications</a>
      </li>
    </ul>
  </div>
</nav>
<div class="container">

  <table class="table table-striped table-bordered">
    <tr>
      <th>Notification</th>
      <th>Ligne</th>
      <th>Date</th>
    </tr>
    <% for (Notification not : nts) {%>
    <tr>
      <td><a href="/comments?notificationId=<%=not.getNotificationId()%>"><%= not.getNotificationText() %></a>
      </td>
      <td><%= not.getLine().getLineName() %>
      </td>
      <td><%= not.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %>
      </td>
    </tr>
    <%
      }
    %>
  </table>
</div>
<%@ include file="parts/footer.jsp" %>
</body>
</html>

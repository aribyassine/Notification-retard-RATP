<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page import="model.entities.Notification" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.entities.Comment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%
  Notification notification = (Notification) request.getAttribute("notification");
  List<Comment> coms = new ArrayList<>(notification.getComments());
%>
<html>
<head>
  <%@ include file="parts/meta.jsp" %>
  <title>Comments</title>
  <%@ include file="parts/head.jsp" %>
  <style>
    textarea {
      resize: none;
    }
  </style>
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

  <div class="panel panel-default">
    <div class="panel-heading"><h2><%=notification.getNotificationText()%>
    </h2> <%= notification.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %>
    </div>
    <div class="panel-body">
      <div class="table-responsive">
        <table class="table table-hover table-bordered">
          <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Commentaire</th>
          </tr>
          <% int i = 1; %>
          <% for (Comment com : coms) {%>

          <tr>
            <td class="row"><%=i++%>
            </td>
            <td><%=com.getClient().getUserName()%>
            </td>
            <td><%=com.getCommentText()%>
            </td>
          </tr>
          <%}%>
        </table>
      </div>
      <form method="post" action="comments">
        <div class="form-group">
          <textarea class="form-control" name="com" placeholder="Ajoutez un commentaire" rows="3"></textarea>
        </div>
        <input id="prodId" name="notificationId" type="hidden" value="<%=notification.getNotificationId()%>">
        <button type="submit" class="btn btn-default">Envoyer</button>
      </form>
    </div>
  </div>
</div>
<%@ include file="parts/footer.jsp" %>

</body>
</html>

<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>Inscription</title>
    <%@ include file="parts/head.jsp" %>
</head>
<body>
<%@ include file="parts/header.jsp" %>

<div class="container">

  <c:if test="${not empty err}" >
      <div class="alert alert-danger alert-dismissable fade in">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong>Erreur :</strong> ${err}
      </div>
  </c:if>

    <div class="panel panel-default center-block">
        <div class="panel-heading">
            <h3 class="panel-title">Vous pouvez vous inscrire via ce formulaire.</h3>
        </div>
        <div class="panel-body">
            <form method="post" action="register">
                <div class="form-group">
                    <label for="login">Nom d'utilisateur</label>
                    <input type="text" class="form-control" id="login" name="login" placeholder="Login" value="<c:out value='${param.login}'/>" required>
                </div>
                <div class="form-group">
                    <label for="login">Adresse email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Email" value="<c:out value='${param.email}'/>" required>
                </div>
                <div class="form-group">
                    <label for="login">Numéro de téléphone</label>
                    <input type="text" pattern="[0-9]{10}" class="form-control" id="phone" name="phone" placeholder="Phone number" value="<c:out value='${param.phone}'/>" required>
                </div>
                <div class="form-group">
                    <label for="pwd">Mot de passe</label>
                    <input type="password" class="form-control" id="pwd" name="pwd" placeholder="Password" required>
                </div>
                <button type="submit" class="btn btn-default">Inscription</button>
            </form>
        </div>
    </div>
</div>
<%@ include file="parts/footer.jsp" %>
</body>
</html>

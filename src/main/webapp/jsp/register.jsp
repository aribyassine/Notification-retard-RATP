<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>Register</title>
    <%@ include file="parts/head.jsp" %>
</head>
<body>
<%@ include file="parts/header.jsp" %>
<div class="container">
    <div class="panel panel-default center-block">
        <div class="panel-heading">
            <h3 class="panel-title">Register form</h3>
        </div>
        <div class="panel-body">
            <form method="post" action="register">
                <div class="form-group">
                    <label for="login">User name</label>
                    <input type="text" class="form-control" id="login" name="login" placeholder="Login" required>
                </div>
                <div class="form-group">
                    <label for="login">Email address</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Email" required>
                </div>
                <div class="form-group">
                    <label for="login">Phone number</label>
                    <input type="text" pattern="[0-9]{10}" class="form-control" id="phone" name="phone" placeholder="Phone number" required>
                </div>
                <div class="form-group">
                    <label for="pwd">Password</label>
                    <input type="password" class="form-control" id="pwd" name="pwd" placeholder="Password" required>
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
        </div>
    </div>
</div>
<%@ include file="parts/footer.jsp" %>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>REGISTRATION</title>

   <link href="/css/styleRegistration.css" rel="stylesheet">

</head>

<body>
<div id="registration">
<form:form method="POST" modelAttribute="userForm">
    <h2>REGISTRATION FORM</h2>
    <div>
      <form:input type="text" minlength="4" path="username" placeholder="Username"
                  autofocus="true"></form:input>
      <form:errors path="username"></form:errors>
        ${usernameError}
    </div>
    <div>
      <form:input type="password" path="password" placeholder="Password"></form:input>
    </div>
    <div>
      <form:input type="password" minlength="3" path="passwordConfirm"
                  placeholder="Confirm your password"></form:input>
      <form:errors path="password"></form:errors>
        ${passwordError}

    </div>

<button type="submit">REGISTER YOURSELF AS USER</button>
</form:form>
<div id="controls">
   <button id="logInButton" type="button" class="buttonLogIn" onclick="logIn()" >LOGIN</button>
</div>


<img class="displayed" src="../img/black.png" alt="" width="240" height="384" >


<script>
function logIn() {
 window.location='/hello';
}
</script>
</body>
</html>
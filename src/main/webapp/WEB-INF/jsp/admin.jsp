<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>GameAdministration</title>
  <link href="/css/styleAdminPage.css" rel="stylesheet">

  <style>
          h1 {
             text-align: center;
          },
          h2 {
              text-align: center;
          },
          table,
          thead,
          td,
          th {
              border: 1px solid;
              padding: 20px;
              text-align: center;
          }

          table {
              text-align: center;
          }

          thead {
              text-align: center;
         }
      </style>
</head>

<body>
<div
id="user">
<h1>ALL REGISTERED USERS</h1>
  <table
  id="t1">
    <thead>
      <tr>
         <th>ID</th>
         <th>USER</th>
         <th>PASSWORD</th>
      </tr>
      </thead>

    <c:forEach items="${allUsers}" var="user">
          <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>
              <c:forEach items="${user.roles}" var="role">${role.name}; </c:forEach>
            </td>
            <td>
               <button type="submit"  onclick=window.location='/admin/${user.id}' >Delete</button>
            </td>
          </tr>
    </c:forEach>
  </table>

<h2>USERS BEST SCORES</h2>
  <table
  id="t2">
      <thead>
        <tr>
          <th>ID</th>
          <th>PLAYER</th>
          <th>SCORE</th>
        </tr>
        </thead>
      <c:forEach items="${playersResults}" var="player">
                    <tr>
                      <td>${player.id}</td>
                      <td>${player.playerName}</td>
                      <td>${player.playerScore}</td>
                    </tr>
      </c:forEach>
  </table>
  <a href="/profile">Return</a>
</div>

<div id="images">
   <img class="displayed" src="../img/black.png" alt="" width="240" height="384" >
</div>

</body>
</html>
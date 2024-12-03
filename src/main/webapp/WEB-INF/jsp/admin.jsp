<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>GameAdministration</title>
</head>

<body>
<div>
  <table>
    <thead>
    <th>UserName</th>
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
  <a href="/profile">Return</a>
</div>


</body>
</html>
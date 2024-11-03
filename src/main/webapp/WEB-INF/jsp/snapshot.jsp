<%@ page import="com.app.game.tetris.serviceImpl.State" %>
<%@ page import="com.app.game.tetris.serviceImpl.Stage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tetris</title>

    <link href="/css/styleGamePage.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <script src="<c:url value="/jquery-3.6.0.min.js"/>"></script>
</head>
<body>

<h1 text align="center"> <b id="tetrisSpeedBox"> TETRIS  at speed ${stepdown} </b> </h1>

<div id="header"
  <h1> </h1>
  <h2 id="gameStatusBox">  Game status: ${gameStatus} </h2>
</div>




<div id="content"

      <div class="tetris-wrapper"</div>

       <table align="center" style="background-color: #ffffff; border:1px black solid;" border="1" width="600">
         <tr>
         <td> <b> Player </b> </td>
         <td id="playerBox"> ${player}</td>
         <td> <b> Score </b> </td>
         <td id="playerScoreBox"> ${score}</td>
         </tr>
         <tr>
         <td> <b> Best Player </b></td>
         <td id="bestPlayerBox">  ${bestplayer}</td>
         <td> <b> Best Score </b> </td>
         <td id="bestPlayerScoreBox">  ${bestscore}</td>
         </tr>
         </table>

     <table id= "table" align="center" style="background-color: #ffffff; border:1px black solid;">
                	<tr>
                	<td><img id="c0v0" src=${cells0v0}></td>
                  <td><img id="c0v1" src=${cells0v1}></td>
                  <td><img id="c0v2" src=${cells0v2}></td>
                  <td><img id="c0v3" src=${cells0v3}></td>
                  <td><img id="c0v4" src=${cells0v4}></td>
                  <td><img id="c0v5" src=${cells0v5}></td>
                  <td><img id="c0v6" src=${cells0v6}></td>
                  <td><img id="c0v7" src=${cells0v7}></td>
                  <td><img id="c0v8" src=${cells0v8}></td>
                  <td><img id="c0v9" src=${cells0v9}></td>
                  <td><img id="c0v10" src=${cells0v10}></td>
                  <td><img id="c0v11" src=${cells0v11}></td>
                	</tr>
                	<tr>
                  <td><img id="c1v0"src=${cells1v0}></td>
                  <td><img id="c1v1"src=${cells1v1}></td>
                  <td><img id="c1v2"src=${cells1v2}></td>
                  <td><img id="c1v3"src=${cells1v3}></td>
                  <td><img id="c1v4"src=${cells1v4}></td>
                  <td><img id="c1v5"src=${cells1v5}></td>
                  <td><img id="c1v6"src=${cells1v6}></td>
                  <td><img id="c1v7"src=${cells1v7}></td>
                  <td><img id="c1v8"src=${cells1v8}></td>
                  <td><img id="c1v9"src=${cells1v9}></td>
                  <td><img id="c1v10"src=${cells1v10}></td>
                  <td><img id="c1v11"src=${cells1v11}></td>
                	 </tr>
                	<tr>
                  <td><img id="c2v0" src=${cells2v0}></td>
                  <td><img id="c2v1"src=${cells2v1}></td>
                  <td><img id="c2v2"src=${cells2v2}></td>
                  <td><img id="c2v3"src=${cells2v3}></td>
                  <td><img id="c2v4"src=${cells2v4}></td>
                  <td><img id="c2v5"src=${cells2v5}></td>
                  <td><img id="c2v6"src=${cells2v6}></td>
                  <td><img id="c2v7"src=${cells2v7}></td>
                  <td><img id="c2v8"src=${cells2v8}></td>
                  <td><img id="c2v9"src=${cells2v9}></td>
                  <td><img id="c2v10"src=${cells2v10}></td>
                  <td><img id="c2v11"src=${cells2v11}></td>
                	</tr>
                	<tr>
                	<td><img id="c3v0" src=${cells3v0}></td>
                  <td><img id="c3v1" src=${cells3v1}></td>
                  <td><img id="c3v2" src=${cells3v2}></td>
                  <td><img id="c3v3" src=${cells3v3}></td>
                  <td><img id="c3v4" src=${cells3v4}></td>
                  <td><img id="c3v5" src=${cells3v5}></td>
                  <td><img id="c3v6" src=${cells3v6}></td>
                  <td><img id="c3v7" src=${cells3v7}></td>
                  <td><img id="c3v8" src=${cells3v8}></td>
                  <td><img id="c3v9" src=${cells3v9}></td>
                  <td><img id="c3v10" src=${cells3v10}></td>
                  <td><img id="c3v11" src=${cells3v11}></td>
                	</tr>
                	<tr>
                  <td><img id="c4v0" src=${cells4v0}></td>
                  <td><img id="c4v1" src=${cells4v1}></td>
                  <td><img id="c4v2" src=${cells4v2}></td>
                  <td><img id="c4v3" src=${cells4v3}></td>
                  <td><img id="c4v4" src=${cells4v4}></td>
                  <td><img id="c4v5" src=${cells4v5}></td>
                  <td><img id="c4v6" src=${cells4v6}></td>
                  <td><img id="c4v7" src=${cells4v7}></td>
                  <td><img id="c4v8" src=${cells4v8}></td>
                  <td><img id="c4v9" src=${cells4v9}></td>
                  <td><img id="c4v10" src=${cells4v10}></td>
                  <td><img id="c4v11" src=${cells4v11}></td>
                	</tr>
                  <tr>
                 	<td><img id="c5v0" src=${cells5v0}></td>
                 	<td><img id="c5v1" src=${cells5v1}></td>
                 	<td><img id="c5v2" src=${cells5v2}></td>
                 	<td><img id="c5v3" src=${cells5v3}></td>
                 	<td><img id="c5v4" src=${cells5v4}></td>
                 	<td><img id="c5v5" src=${cells5v5}></td>
                 	<td><img id="c5v6" src=${cells5v6}></td>
                 	<td><img id="c5v7" src=${cells5v7}></td>
                 	<td><img id="c5v8" src=${cells5v8}></td>
                 	<td><img id="c5v9" src=${cells5v9}></td>
                 	<td><img id="c5v10"src=${cells5v10}></td>
                 	<td><img id="c5v11" src=${cells5v11}></td>

                  </tr>
                  <tr>
                  <td><img id="c6v0" src=${cells6v0}></td>
                  <td><img id="c6v1" src=${cells6v1}></td>
                  <td><img id="c6v2" src=${cells6v2}></td>
                  <td><img id="c6v3" src=${cells6v3}></td>
                  <td><img id="c6v4" src=${cells6v4}></td>
                  <td><img id="c6v5" src=${cells6v5}></td>
                  <td><img id="c6v6" src=${cells6v6}></td>
                  <td><img id="c6v7" src=${cells6v7}></td>
                  <td><img id="c6v8" src=${cells6v8}></td>
                  <td><img id="c6v9" src=${cells6v9}></td>
                  <td><img id="c6v10" src=${cells6v10}></td>
                  <td><img id="c6v11" src=${cells6v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c7v0" src=${cells7v0}></td>
                  <td><img id="c7v1" src=${cells7v1}></td>
                  <td><img id="c7v2" src=${cells7v2}></td>
                  <td><img id="c7v3" src=${cells7v3}></td>
                  <td><img id="c7v4" src=${cells7v4}></td>
                  <td><img id="c7v5" src=${cells7v5}></td>
                  <td><img id="c7v6" src=${cells7v6}></td>
                  <td><img id="c7v7" src=${cells7v7}></td>
                  <td><img id="c7v8" src=${cells7v8}></td>
                  <td><img id="c7v9" src=${cells7v9}></td>
                  <td><img id="c7v10"src=${cells7v10}></td>
                  <td><img id="c7v11" src=${cells7v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c8v0" src=${cells8v0}></td>
                  <td><img id="c8v1" src=${cells8v1}></td>
                  <td><img id="c8v2" src=${cells8v2}></td>
                  <td><img id="c8v3" src=${cells8v3}></td>
                  <td><img id="c8v4" src=${cells8v4}></td>
                  <td><img id="c8v5" src=${cells8v5}></td>
                  <td><img id="c8v6" src=${cells8v6}></td>
                  <td><img id="c8v7" src=${cells8v7}></td>
                  <td><img id="c8v8" src=${cells8v8}></td>
                  <td><img id="c8v9" src=${cells8v9}></td>
                  <td><img id="c8v10" src=${cells8v10}></td>
                  <td><img id="c8v11" src=${cells8v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c9v0" src=${cells9v0}></td>
                  <td><img id="c9v1" src=${cells9v1}></td>
                  <td><img id="c9v2" src=${cells9v2}></td>
                  <td><img id="c9v3" src=${cells9v3}></td>
                  <td><img id="c9v4" src=${cells9v4}></td>
                  <td><img id="c9v5" src=${cells9v5}></td>
                  <td><img id="c9v6" src=${cells9v6}></td>
                  <td><img id="c9v7" src=${cells9v7}></td>
                  <td><img id="c9v8" src=${cells9v8}></td>
                  <td><img id="c9v9" src=${cells9v9}></td>
                  <td><img id="c9v10" src=${cells9v10}></td>
                  <td><img id="c9v11" src=${cells9v11}></td>
                  </tr>
                	<tr>
                  <td><img id="c10v0" src=${cells10v0}></td>
                  <td><img id="c10v1" src=${cells10v1}></td>
                  <td><img id="c10v2" src=${cells10v2}></td>
                  <td><img id="c10v3" src=${cells10v3}></td>
                  <td><img id="c10v4" src=${cells10v4}></td>
                  <td><img id="c10v5" src=${cells10v5}></td>
                  <td><img id="c10v6" src=${cells10v6}></td>
                  <td><img id="c10v7" src=${cells10v7}></td>
                  <td><img id="c10v8" src=${cells10v8}></td>
                  <td><img id="c10v9" src=${cells10v9}></td>
                  <td><img id="c10v10" src=${cells10v10}></td>
                  <td><img id="c10v11" src=${cells10v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c11v0" src=${cells11v0}></td>
                  <td><img id="c11v1" src=${cells11v1}></td>
                  <td><img id="c11v2" src=${cells11v2}></td>
                  <td><img id="c11v3" src=${cells11v3}></td>
                  <td><img id="c11v4" src=${cells11v4}></td>
                  <td><img id="c11v5" src=${cells11v5}></td>
                  <td><img id="c11v6" src=${cells11v6}></td>
                  <td><img id="c11v7" src=${cells11v7}></td>
                  <td><img id="c11v8" src=${cells11v8}></td>
                  <td><img id="c11v9" src=${cells11v9}></td>
                  <td><img id="c11v10"src=${cells11v10}></td>
                  <td><img id="c11v11" src=${cells11v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c12v0" src=${cells12v0}></td>
                  <td><img id="c12v1" src=${cells12v1}></td>
                  <td><img id="c12v2" src=${cells12v2}></td>
                  <td><img id="c12v3" src=${cells12v3}></td>
                  <td><img id="c12v4" src=${cells12v4}></td>
                  <td><img id="c12v5" src=${cells12v5}></td>
                  <td><img id="c12v6" src=${cells12v6}></td>
                  <td><img id="c12v7" src=${cells12v7}></td>
                  <td><img id="c12v8" src=${cells12v8}></td>
                  <td><img id="c12v9" src=${cells12v9}></td>
                  <td><img id="c12v10" src=${cells12v10}></td>
                  <td><img id="c12v11" src=${cells12v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c13v0" src=${cells13v0}></td>
                  <td><img id="c13v1" src=${cells13v1}></td>
                  <td><img id="c13v2" src=${cells13v2}></td>
                  <td><img id="c13v3" src=${cells13v3}></td>
                  <td><img id="c13v4" src=${cells13v4}></td>
                  <td><img id="c13v5" src=${cells13v5}></td>
                  <td><img id="c13v6" src=${cells13v6}></td>
                  <td><img id="c13v7" src=${cells13v7}></td>
                  <td><img id="c13v8" src=${cells13v8}></td>
                  <td><img id="c13v9" src=${cells13v9}></td>
                  <td><img id="c13v10" src=${cells13v10}></td>
                  <td><img id="c13v11" src=${cells13v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c14v0" src=${cells14v0}></td>
                  <td><img id="c14v1" src=${cells14v1}></td>
                  <td><img id="c14v2" src=${cells14v2}></td>
                  <td><img id="c14v3" src=${cells14v3}></td>
                  <td><img id="c14v4" src=${cells14v4}></td>
                  <td><img id="c14v5" src=${cells14v5}></td>
                  <td><img id="c14v6" src=${cells14v6}></td>
                  <td><img id="c14v7" src=${cells14v7}></td>
                  <td><img id="c14v8" src=${cells14v8}></td>
                  <td><img id="c14v9" src=${cells14v9}></td>
                  <td><img id="c14v10" src=${cells14v10}></td>
                  <td><img id="c14v11" src=${cells14v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c15v0" src=${cells15v0}></td>
                  <td><img id="c15v1" src=${cells15v1}></td>
                  <td><img id="c15v2" src=${cells15v2}></td>
                  <td><img id="c15v3" src=${cells15v3}></td>
                  <td><img id="c15v4" src=${cells15v4}></td>
                  <td><img id="c15v5" src=${cells15v5}></td>
                  <td><img id="c15v6" src=${cells15v6}></td>
                  <td><img id="c15v7" src=${cells15v7}></td>
                  <td><img id="c15v8" src=${cells15v8}></td>
                  <td><img id="c15v9" src=${cells15v9}></td>
                  <td><img id="c15v10" src=${cells15v10}></td>
                  <td><img id="c15v11" src=${cells15v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c16v0" src=${cells16v0}></td>
                  <td><img id="c16v1" src=${cells16v1}></td>
                  <td><img id="c16v2" src=${cells16v2}></td>
                  <td><img id="c16v3" src=${cells16v3}></td>
                  <td><img id="c16v4" src=${cells16v4}></td>
                  <td><img id="c16v5" src=${cells16v5}></td>
                  <td><img id="c16v6" src=${cells16v6}></td>
                  <td><img id="c16v7" src=${cells16v7}></td>
                  <td><img id="c16v8" src=${cells16v8}></td>
                  <td><img id="c16v9" src=${cells16v9}></td>
                  <td><img id="c16v10" src=${cells16v10}></td>
                  <td><img id="c16v11" src=${cells16v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c17v0" src=${cells17v0}></td>
                  <td><img id="c17v1" src=${cells17v1}></td>
                  <td><img id="c17v2" src=${cells17v2}></td>
                  <td><img id="c17v3" src=${cells17v3}></td>
                  <td><img id="c17v4" src=${cells17v4}></td>
                  <td><img id="c17v5" src=${cells17v5}></td>
                  <td><img id="c17v6" src=${cells17v6}></td>
                  <td><img id="c17v7" src=${cells17v7}></td>
                  <td><img id="c17v8" src=${cells17v8}></td>
                  <td><img id="c17v9" src=${cells17v9}></td>
                  <td><img id="c17v10" src=${cells17v10}></td>
                  <td><img id="c17v11" src=${cells17v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c18v0" src=${cells18v0}></td>
                  <td><img id="c18v1" src=${cells18v1}></td>
                  <td><img id="c18v2" src=${cells18v2}></td>
                  <td><img id="c18v3" src=${cells18v3}></td>
                  <td><img id="c18v4" src=${cells18v4}></td>
                  <td><img id="c18v5" src=${cells18v5}></td>
                  <td><img id="c18v6" src=${cells18v6}></td>
                  <td><img id="c18v7" src=${cells18v7}></td>
                  <td><img id="c18v8" src=${cells18v8}></td>
                  <td><img id="c18v9" src=${cells18v9}></td>
                  <td><img id="c18v10" src=${cells18v10}></td>
                  <td><img id="c18v11" src=${cells18v11}></td>
                  </tr>
                  <tr>
                  <td><img id="c19v0" src=${cells19v0}></td>
                  <td><img id="c19v1" src=${cells19v1}></td>
                  <td><img id="c19v2" src=${cells19v2}></td>
                  <td><img id="c19v3" src=${cells19v3}></td>
                  <td><img id="c19v4" src=${cells19v4}></td>
                  <td><img id="c19v5" src=${cells19v5}></td>
                  <td><img id="c19v6" src=${cells19v6}></td>
                  <td><img id="c19v7" src=${cells19v7}></td>
                  <td><img id="c19v8" src=${cells19v8}></td>
                  <td><img id="c19v9" src=${cells19v9}></td>
                  <td><img id="c19v10" src=${cells19v10}></td>
                  <td><img id="c19v11" src=${cells19v11}></td>
                   </tr>
               </table>
</div>

<div id="controls">
   <button id="newGameButton" onclick="newgame()" >NewGame</button>
</div>

<script>



function newgame() {
 window.location='/hello';
 }





</script>

</body>
</html>
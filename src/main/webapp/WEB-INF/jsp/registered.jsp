<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>YOU HAVE BEEN SUCCESSFULLY REGISTERED AS NEW PLAYER!!!</title>

  <link href="/css/styleRegistered.css" rel="stylesheet">

</head>

<body>

<h1 text align="center"> <b id="registeredBox"> YOU HAVE BEEN SUCCESSFULLY REGISTERED AS NEW PLAYER!!!</b> </h1>


<div id="images">
   <img class="displayed" src="../img/black.png" alt="" width="240" height="384" >
</div>

<div id="controls">
   <button id="logInButton" type="button" class="buttonLogIn" onclick="logIn()" >LOGIN</button>
</div>


<script>
function logIn() {
 window.location='/hello';
}
</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>

<form action="/signIn" method="post" class="form">
    <p>Sign In</p>
    <p>
        <label for="login">Login<input id="login" type="text" name="login"></label><br>
        <label for="password">Password<input id="password" type="password" name="password"></label><br>

    <p><button type="submit">Вход</button></p><br>
</form>
<h3>${signInStatus}</h3>

</body>
</html>

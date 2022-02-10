<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Profile</title>
    <script src="<c:url value="/js/audio_loader.js"/>"></script>

</head>
<body>

<p>${user.firstName}</p>
<p>${user.lastName}</p>

<a href="/products">Продукты</a>

<div id="audios">
    <c:forEach items="${favorites}" var="favorite">
        <div id="block-${favorite.getId()}" style="margin-bottom: 10px">
            <div id="audio-${favorite.getId()}"> ${favorite.getTitle()} </div>
            <button class="play" id="play-button-${favorite.getId()}">Play</button>
            <button class="stop" id="stop-button-${favorite.getId()}">Stop</button>
            <button class="delete" id="delete-${favorite.getId()}">Delete from favorites</button>
        </div>
    </c:forEach>
</div>

</body>
</html>

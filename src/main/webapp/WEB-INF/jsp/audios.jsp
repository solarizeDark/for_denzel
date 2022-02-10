<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Audios</title>
    <script src="<c:url value="/js/audio_loader.js"/>"></script>
</head>
<body>
    <form action="audios" method="post" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="submit" />
    </form>

    <c:if test="${answer != null}">
        ${answer}
    </c:if>

    <div id="audios">
        <c:forEach items="${audios}" var="favorite">
            <div id="block-${favorite.getId()}" style="margin-bottom: 10px">
                <div id="audio-${favorite.getId()}"> ${favorite.getTitle()} </div>
                <button class="play" id="play-button-${favorite.getId()}">Play</button>
                <button class="stop" id="stop-button-${favorite.getId()}">Stop</button>
                <button class="add" id="add-favorites-${favorite.getId()}">Add to favorites</button>
            </div>
        </c:forEach>
    </div>
</body>
</html>

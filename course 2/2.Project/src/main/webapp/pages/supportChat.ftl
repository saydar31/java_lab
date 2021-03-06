<html lang="en">
<head>
    <title>SupportChat</title>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body onload="receive()">
<ul id="messages">
    <#list dialog.messages as message>
        <li>${message.userDto.firstName} ${message.userDto.lastName}:${message.text}</li>
    </#list>
</ul>
<label><textarea rows="20" cols="50" id="textarea"></textarea></label>
<input id="receiverId" type="hidden" value="${receiverId}">
<input type="hidden" id="senderId" value="${senderId}">
<button onclick="send()">send</button>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    function receive() {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax(
            {
                url: "${sendingUrl}",
                method: "GET",
                processData: false,
                contentType: false,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header,token)
                },
                success: function (response) {
                    response.forEach(element =>
                        $('#messages').append("<li>" + element['userDto']['firstName'] + ' ' + element['userDto']['lastName'] + ': ' + element['text'] + "</li>")
                    );
                    receive();
                }
            }
        )
    }

    function send() {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        let formData = new FormData();
        formData.append('text', document.getElementById('textarea').value);
        formData.append('senderId', document.getElementById('senderId').value)
        $.ajax(
            {
                url: "${sendingUrl}",
                method: "POST",
                data: formData,
                processData: false,
                contentType: false,
                complete: function () {

                },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header,token)
                },
            }
        )
    }
</script>
</body>

</html>
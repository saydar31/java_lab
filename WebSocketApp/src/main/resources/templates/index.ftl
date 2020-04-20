<html lang="en">
<head>
    <title id="title">Chat</title>
    <meta charset="UTF-8">
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
</head>
<body onload="init()">
<div id="signInDiv">
    <label>
        <input type="text" placeholder="user name" id="userName">
    </label><br>
    <label>
        <input type="password" placeholder="password" id="password">
    </label><br>
    <button onclick="signIn()">Sign In</button>
</div>
<div id="roomListDiv">
    <ul id="roomList">

    </ul>
    <label>
        <input type="text" id="newRoomName" placeholder="newRoomName">
        <button onclick="addRoom()">add</button>
    </label>
</div>
<div id="messageListDiv">
    <ul id="messageList">

    </ul>
    <label for="messageIn"></label><input type="text" id="messageIn" placeholder="message">
    <button id="messageButton" onclick="sendMessage()">send</button>
</div>
<input type="hidden" id="chatIdHandler">
<script
        src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script>
    let roomWebSocket;
    let signInWebSocket;
    let chatWebSocket;
    let currentChatId;

    function connect() {
        let token = check_cookie_name('X-Authorization');
        if (token !== '') {
            connectRoomList()
        } else {
            signInWebSocket = new SockJS("http://localhost:8080/signIn");
            $('#signInDiv').show();
            signInWebSocket.onmessage = function (response) {
                let data = response['data'];
                let json = JSON.parse(data);
                console.log(json['success'])
                if (json['success']) {
                    $('#signInDiv').hide();
                    signInWebSocket.close();
                    document.cookie = 'X-Authorization=' + json['token'] + ';path=/';
                    connectRoomList()
                }
            }
        }
    }

    function connectRoomList() {
        roomWebSocket = new SockJS("http://localhost:8080/rooms");
        $('#roomListDiv').show();
        roomWebSocket.onmessage = function (response) {
            let data = response['data'];
            let json = JSON.parse(data);
            json['rooms'].forEach(
                element => $('#roomList').append('<li><button onclick="joinChat(' + element['id'] + ')">' + element['name'] + '</button></li>')
            )
        }
    }

    function joinChat(chatId) {
        currentChatId = chatId;
        roomWebSocket.close();
        $('#roomListDiv').hide();
        $('#messageListDiv').show();
        $('#messageList').show();
        chatWebSocket = new SockJS("http://localhost:8080/chat");
        chatWebSocket.onmessage = function (response) {
            let data = response['data'];
            let json = JSON.parse(data);
            console.log(JSON.stringify(data))
            json['messages'].forEach(
                element => {
                    $('#messageList').append('<li>' + element['author']['userName'] + ': ' + element['value'])
                    console.log(JSON.stringify(element));

                }
            )
        }
        let message = {
            "init": true,
            "authentication": check_cookie_name('X-Authorization'),
            "message": "login",
            "chatId": currentChatId
        }
        chatWebSocket.onopen = function () {
            chatWebSocket.send(JSON.stringify(message))
        }
    }

    function sendMessage() {
        let message = {
            "init": false,
            "authentication": check_cookie_name('X-Authorization'),
            "message": $('#messageIn').val(),
            "chatId": currentChatId
        }
        chatWebSocket.send(JSON.stringify(message));
    }

    function signIn() {
        let signInDto = {
            "userName": $('#userName').val(),
            "password": $('#password').val()
        }
        signInWebSocket.send(JSON.stringify(signInDto));
    }

    function addRoom() {
        let message = {
            "authentication": check_cookie_name('X-Authorization'),
            "roomName": $('#newRoomName').val()
        };
        roomWebSocket.send(JSON.stringify(message));
    }

    function init() {
        $('#signInDiv').hide();
        console.log("hide sign in")
        $('#roomListDiv').hide();
        console.log("hide room")
        $('#messageListDiv').hide();
        console.log("hide messages")
        connect();
    }

    function check_cookie_name(name) {
        let match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
        if (match) {
            console.log(match[2])
            return match[2];
        } else {
            return '';
        }
    }
</script>
</body>
</html>
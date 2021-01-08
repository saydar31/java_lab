<html>
<#import "/spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><@spring.message "sign.in.title"></@spring.message></title>
</head>
<body>
<form action="/sign_in" method="post">
    <input hidden type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label><@spring.message "sign.in.email.field"/>:<input type="email" name="email"></label><br>
    <label><@spring.message "sign.in.password.field"/>:<input type="password" name="password"></label><br>
    <label><@spring.message "sign.in.remember-me"/>:<input type="checkbox" name="remember-me"></label>
    <input type="submit" value="<@spring.message "sign.in.title"></@spring.message>">
</form>
</body>
</html>
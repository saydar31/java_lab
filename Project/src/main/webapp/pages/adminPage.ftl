<html lang="en">
<head>
    <title>Admin page</title>
    <meta charset="UTF-8">
</head>
<body>
<ul>
    <#list users as user>
    <li>${user.email}</li>
    </#list>
</ul>
</body>
</html>
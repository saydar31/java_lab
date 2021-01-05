<html lang="en">
<#import "/spring.ftl" as spring/>
<head>
    <title><@spring.message "admin.page.title"></@spring.message></title>
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
<html lang="en">
<head>
    <title>forums</title>
    <meta charset="UTF-8">
</head>
<body>
<h1>Subjects</h1><br>
<form action="/forum" method="post">
    <input hidden type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label>
        <input type="text" name="name">
    </label>
    <label>
        <input type="text" name="tags">
    </label>
    <input type="submit" value="New discussion">
</form>
<ul>
    <#list discussions as discussion>
        <li><a href="/forum/${discussion.id}">${discussion.name}</a>
            <ul>
                <#list discussion.tags as tag>
                    <li>${tag}</li>
                </#list>
            </ul>
        </li>
    </#list>
</ul>
<br>
<#if page gt 0>
    <a href="/forum?p=${page-1}&s=${size}">
        <button><</button>
    </a>
</#if>
<a href="/forum?p=${page+1}&s=${size}">
    <button>></button>
</a>
</body>
</html>
<html lang="en">
<head>
    <title>${discussion.name}</title>
    <meta charset="UTF-8">
</head>
<body>
<style>
    .outline {
        border: 1px solid #000000;
        padding: 0 10px;
    }
    hr {
        border: none; /* Убираем границу */
        background-color: black; /* Цвет линии */
        color: black; /* Цвет линии для IE6-7 */
        height: 2px; /* Толщина линии */
    }
</style>
<h1>${discussion.name}</h1>
<ul>
    <#list discussion.tags as tag>
        <li>${tag}</li>
    </#list>
</ul>
<#if page gt 0>
    <a href="/forum/${discussion.id}?p=${page-1}&s=${size}">
        <button><</button>
    </a>
</#if>
<a href="/forum/${discussion.id}?p=${page+1}&s=${size}">
    <button><</button>
</a>
<#list discussion.records as forumRecord>
    <div class="outline">
    <p>${forumRecord.user.firstName} ${forumRecord.user.lastName}: ${forumRecord.date.month}/${forumRecord.date.dayOfMonth}/${forumRecord.date.year} ${forumRecord.date.hour}:${forumRecord.date.minute}:${forumRecord.date.second}</p><hr>
    <p>${forumRecord.message}</p>
    </div>
</#list>
<form action="/forum/${discussion.id}" method="post">
    <label>
        <textarea name="message" rows="15" cols="40"></textarea><br>
    </label>
    <input type="submit" value="Отправить">
</form>
</body>
</html>
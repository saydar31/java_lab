<html lang="en">
<head>
    <title>${discussion.name}</title>
    <meta charset="UTF-8">
    <#import "spring.ftl" as spring/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
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
<div id="discussion">
    <#list discussion.records as forumRecord>
        <div class="outline">
            <p>${forumRecord.user.firstName} ${forumRecord.user.lastName}: ${forumRecord.date.month}
                /${forumRecord.date.dayOfMonth}/${forumRecord.date.year} ${forumRecord.date.hour}
                :${forumRecord.date.minute}
                :${forumRecord.date.second}</p>
            <hr>
            <p>${forumRecord.message}</p>
        </div>
    </#list>
</div>

<style>
    .error {
        color: #f44336;
    }
</style>
<p id="errorPlace" class="error"></p>
<label for="newRecord">New record<br></label>
<textarea name="newRecord" id="newRecord"></textarea><br>
<button onclick="send()">Send</button>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    function send() {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $('#errorPlace').hide();
        let body = {
            "message": $('#newRecord').val()
        };
        $.ajax(
            {
                type: "POST",
                url: "/forum/${discussion.id}",
                contentType: "application/json",
                data: JSON.stringify(body),
                error: function (error) {
                    console.log(JSON.stringify(error))
                    let responseText = error['responseText']
                    let result = JSON.parse(responseText);
                    $('#errorPlace').text(result['message'])
                    $('#errorPlace').show()
                },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token)
                }
            }
        ).done(function (response) {
            console.log(JSON.stringify(response))
            let userName = response['user']['firstName'] + " " + response['user']['lastName'];
            let time = response['date']['month'] + "/" + response['date']['dayOfMonth'] + "/" + response['date']['year'] + " " +
                response['date']['hour'] + ":" + response['date']['minute'] + ":" + response['date']['second']
            let authorHead = "<p>" + userName + ": " + time + "</p>";
            let message = "<p>" + response['message'] + "</p>";
            $("#discussion").append("<div class='outline'> " + authorHead + "<hr>" + message + "</div>");
        })
    }
</script>
</body>
</html>
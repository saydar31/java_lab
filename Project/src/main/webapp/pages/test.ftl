<html lang="en">
<head>
    <title>test</title>
    <meta charset="UTF-8">
</head>
<body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<h1>${test.name}</h1><br>
<form action="/test" method="post" id="testForm">
    <#list test.testItems as testItem>
        <h2>${testItem.question}</h2><br>
        <#list testItem.answers as answer>
            <#if testItem.multipleChoice>
                <label>
                    <input type="checkbox" name="${testItem.parameterName}" value="${answer}">${answer}<br>
                </label>
            <#else>
                <label>
                    <input type="radio" name="${testItem.parameterName}" value="${answer}">${answer}<br>
                </label>
            </#if>
        </#list>
    </#list>
    <input type="submit" onsubmit="submit()" value="Submit">
</form>
<script>
    function submit() {
        var fd = new FormData(document.querySelector("testForm"));
        alert(JSON.stringify(fd))
        return false;
    }
</script>
</body>
</html>
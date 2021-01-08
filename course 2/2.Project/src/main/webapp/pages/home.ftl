<html lang="en">
<head>
    <title>Ваши файлы</title>
    <meta charset="UTF-8">
</head>
<body>
<#if files?has_content>
    <table border="3">
        <tr>
            <th>File name</th>
        </tr>
        <#list files as file>
            <tr>
                <th><a href="/files/${file.currentPath}">${file.originalName}</a></th>
            </tr>
        </#list>
    </table>
    <#else>
    <p>No files</p>
</#if>
</body>
</html>
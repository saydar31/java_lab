<html lang="en">
<head>
    <title>Editor</title>
</head>
<body>
<h1>${articleMdDto.name}</h1>
<form action="/wiki/article/${articleId}/edit" method="post">
    <label>Version:
        <input type="text" name="version">
    </label><br>
    <label>
<textarea name="newContent" cols="40" rows="60">${articleMdDto.mdContent}</textarea>
    </label>
    <p><input type="submit" value="Update"></p>
</form>
</body>
</html>
<html lang="en">
<head>
    <title>Editor</title>
    <meta charset="UTF-8">
</head>
<body>
<style>
    .center {
        margin: auto;
    }
</style>
<h1>Editor</h1>
<form class="center">
    <input type="text" name="folderId" hidden="hidden" value="${folderId}">
    <label>Name
        <input type="text" name="name" placeholder="name"><br>
    </label>
    <label>Article
        <textarea cols="45" rows="45" name="article" placeholder="write your article here..."></textarea>
    </label>
</form>
</body>
</html>
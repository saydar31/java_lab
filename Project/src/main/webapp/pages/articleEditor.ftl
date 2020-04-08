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
<form class="center" action="/wiki/${folderId}/article/editor" method="post">
    <label>Name
        <input type="text" name="name" placeholder="name"><br>
    </label>
    <label>Article
        <textarea cols="45" rows="45" name="article" placeholder="write your article here..."></textarea>
    </label>
    <input type="submit" value="add">
</form>
</body>
</html>
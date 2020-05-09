<html lang="en">
<head>
    <title>Wiki</title>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<h1>${folder.name}</h1>
<h2>Folders</h2>
<ul id="folderList">
    <#if folder.parentId??>
        <li><a href="/wiki/folder/${folder.parentId}">..</a></li>
    </#if>
    <#list folder.children as folder>
        <li><a href="/wiki/folder/${folder.id}">${folder.name}</a></li>
    </#list>
</ul>

<label>
    <input type="text" id="folderName" name="folderName">
</label>
<button onclick="newFolder()">newFolder</button>
<script>
</script>
<h2>Articles</h2>
<ul id="articleList">
    <#list folder.leafs as leaf>
        <li><a href="/wiki/article/${leaf.id}">${leaf.name}</a></li>
    </#list>
</ul>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<p>add article</p>
<label for="name">article name</label><input type="text" id="name" placeholder="article name">
<input type="file" placeholder="file" id="file" accept="text/markdown">
<button onclick="add()">add</button>
<p><a href="/wiki/article/editor?folderId=${folder.id}">
        <button>add article in editor</button>
    </a></p>
<script>
    function add() {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        let formData = new FormData();
        let files = ($('#file'))[0]['files'];
        [].forEach.call(files, function (file, i, files) {
            formData.append("file", file);
        });
        formData.append("name", document.getElementById("name").value);
        $.ajax({
            type: "POST",
            url: "/wiki/${folder.id}/article/file",
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header,token)
            }
        }).done(function (response) {
            let link = response['article']['id'];
            let aOpen = "<a href='/wiki/article/" + link + "'>";
            $("#articleList").append("<li>" + aOpen + response['article']['name'] + "</a>" + "</li>")
        }).fail(function () {
            alert("err")
        })
    }
</script>
<script>
    function newFolder() {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        let formData = new FormData();
        formData.append("folderName", document.getElementById('folderName').value)
        $.ajax({
            type: "POST",
            url: '/wiki/folder/${folder.id}',
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header,token)
            }
        }).done(function (response) {
            let folderName = response['folderName'];
            let folderId = response['folderId'];
            let aOpen = "<a href='/wiki/folder/" + folderId + "'>";
            $("#folderList").append(("<li>" + aOpen + folderName + "</a>" + "</li>"))
        })
    }
</script>
</body>
</html>
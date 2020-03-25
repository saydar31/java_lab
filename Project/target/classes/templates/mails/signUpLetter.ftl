<html>

<head></head>
<style type="text/css">
    h1 {
        font-size: 120%;
        font-family: Verdana, Arial, Helvetica, sans-serif;
        color: #326625;
    }

    button {
        background-color: #4CAF50; /* Green */
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
    }
</style>

<body>
<h1>Welcome to project</h1>
<p>Dear ${email},</p>
<a href="localhost:8080/proof?hash=${hash}">
    <button>Click here to proof account</button>
</a>
<p>Thanks</p>
</body>

</html>
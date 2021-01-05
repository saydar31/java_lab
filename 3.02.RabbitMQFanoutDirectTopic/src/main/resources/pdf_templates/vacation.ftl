<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title></title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Verdana;

        }

        body {
            font-family: Verdana;
        }

        section {
            display: block;
            margin: 20px 10px;
        }

        .title {
            text-align: center;
        }

        .preface p {
            line-height: 30px;
        }

        .preface p.content {
            text-indent: 2em;
        }

        section > table {
            table-layout: fixed;
            width: 100%;
            margin: 20px 0px;
            text-align: center;
            word-wrap: break-word;
        }

        section table td {
            padding: 5px 0px;
        }
    </style>
</head>
<body>
<section class="title">
    Order Заявление
</section>
<section class="preface">
    <p>Прошу дать отпуск мне ${user.position} ${user.firstName} ${user.lastName} на 1 месяц</p>
</section>
</body>
</html>
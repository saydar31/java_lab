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
    Чек о покупке
</section>
<section class="preface">
    <p>${user.firstName} ${user.lastName} спасибо за premium подписку</p>
    <p>Подписка оформлена на ${subscription.monthCount} месяцев</p>
    <p>Вам доступно больше</p>
</section>
</body>
</html>
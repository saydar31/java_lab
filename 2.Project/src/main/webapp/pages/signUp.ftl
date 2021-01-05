<html>
<head>
    <#import "/spring.ftl" as spring/>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><@spring.message "signUp.title"/></title>
</head>
<style>
    * {box-sizing: border-box}

    /* Full-width input fields */
    input[type=text], input[type=password] {
        width: 100%;
        padding: 15px;
        margin: 5px 0 22px 0;
        display: inline-block;
        border: none;
        background: #f1f1f1;
    }

    input[type=text]:focus, input[type=password]:focus {
        background-color: #ddd;
        outline: none;
    }

    hr {
        border: 1px solid #f1f1f1;
        margin-bottom: 25px;
    }

    /* Set a style for all buttons */
    button {
        background-color: #4CAF50;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        cursor: pointer;
        width: 100%;
        opacity: 0.9;
    }

    button:hover {
        opacity:1;
    }

    /* Extra styles for the cancel button */
    .cancelbtn {
        padding: 14px 20px;
        background-color: #f44336;
    }

    /* Float cancel and signup buttons and add an equal width */
    .cancelbtn, .signupbtn {
        float: left;
        width: 50%;
    }

    /* Add padding to container elements */
    .container {
        padding: 16px;
    }

    /* Clear floats */
    .clearfix::after {
        content: "";
        clear: both;
        display: table;
    }

    .error{
        color: #f44336;
    }

    /* Change styles for cancel button and signup button on extra small screens */
    @media screen and (max-width: 300px) {
        .cancelbtn, .signupbtn {
            width: 100%;
        }
    }
</style>
<body>
<@spring.bind "signUpDto"/>
<form action="/sign_up" method="post" style="border:1px solid #ccc">
    <input hidden type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <div class="container">
        <h1><@spring.message "signUp.title"/></h1>
        <p><@spring.message "signUp.description"/></p>
        <hr>
        <label for="email"><b><@spring.message "signUp.email"/></b></label>
        <label>
            <@spring.formInput "signUpDto.email"/>
            <@spring.showErrors "<br>","error"/>
        </label>
        <label for="psw"><b><@spring.message "signUp.password"/></b></label>
        <label>
            <@spring.formPasswordInput "signUpDto.password"/>
            <@spring.showErrors "<br>","error"/>
        </label>
        <div class="clearfix">
            <button type="submit" class="signupbtn"><@spring.message "signUp.title"/></button>
        </div>
    </div>
</form>
</body>
</html>
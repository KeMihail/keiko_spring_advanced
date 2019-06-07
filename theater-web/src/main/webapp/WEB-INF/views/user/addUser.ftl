<!DOCTYPE html>
<html lang="en" xmlns:form="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>new user</title>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

</head>
<body>

<form action="/user/add" method="post" modelAttribute = "user">

    <div class="row">
        <div class="input-field col s6">
            <input id="firstName" name="firstName" type="text">
            <label for="firstName">first Name</label>
        </div>
        <div class="input-field col s6">
            <input id="lastName" name="lastName" type="text">
            <label for="lastName">last Name</label>
        </div>
        <div class="input-field col s6">
            <input id="email" name="email" type="text">
            <label for="email">email</label>
        </div>
        <div class="input-field col s6">
            <input id="birthday" name="birthday" type="date">
            <label for="birthday">birthday</label>
        </div>
    </div>

    <button class="btn waves-effect waves-light" type="submit" name="action">OK
        <i class="material-icons right"></i>
    </button>

<form>

</body>
</html>
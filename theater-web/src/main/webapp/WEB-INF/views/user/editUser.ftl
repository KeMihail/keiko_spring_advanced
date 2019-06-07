<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>edit user</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>

<form action="/user/add" method="post" modelAttribute="user">

    <input id="id" name="id" value="${user.id}" hidden>

    <div class="row">
        <div class="input-field col s6">
            <input id="firstName" name="firstName" type="text" value="${user.firstName}">
            <label for="firstName">First Name</label>
        </div>
        <div class="input-field col s6">
            <input id="lastName" name="lastName" type="text" value="${user.lastName}">
            <label for="lastName">Last Name</label>
        </div>
        <div class="input-field col s6">
            <input id="email" name="email" type="text" value="${user.email}">
            <label for="email">email</label>
        </div>
        <div class="input-field col s6">
            <input id="birthday" name="birthday" type="date" value="${user.birthday}">
            <label for="birthday">birthday</label>
        </div>
    </div>

    <button class="btn waves-effect waves-light" type="submit" name="action">OK
        <i class="material-icons right"></i>
    </button>

    <form>

</body>
</html>
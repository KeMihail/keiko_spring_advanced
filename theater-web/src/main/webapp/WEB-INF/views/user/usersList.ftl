<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>user list</title>

    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

</head>
<body>


<div class="row">
    <div class="col s6">

        <table class="class=" highlight
        ">

        <tr>
            <td>first name</td>
            <td>last name</td>
            <td>email</td>
            <td>birthday</td>

            <td>actions</td>
            <td></td>
            <td></td>
            <td></td>
        </tr>

        <#list users as user>
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.birthday}</td>

                <td><a href="/user/remove/${user.id}">remove</td>
                <td><a href="/user/update/${user.id}">update</td>
                <td><a href="/booking/user/${user.id}">booking tickets</td>
                <td><a href="/user/showTickets/${user.id}">purchased tickets(pdf)</a></td>
            </tr>
        </#list>

        </table>
    </div>

    <div class="col s6">
        <a class="waves-effect waves-light btn-large" href="/user/add">add user</a>
        <a class="waves-effect waves-light btn-large" href="/">home page</a>
        <p>
            <a class="waves-effect waves-light btn-large" href="/user/usersList.pdf" target="_blank">pdf format</a>
            <a class="waves-effect waves-light btn-large" href="/user/usersList.json" target="_blank">json format</a>
            <a class="waves-effect waves-light btn-large" href="/user/usersList.xml" target="_blank">xml format</a>
    </div>

</div>
</div>

</body>
</html>
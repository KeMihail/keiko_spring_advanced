<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>event list</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>

<div class="row">
    <div class="col s6">

        <table class="class=" highlight
        ">
        <tr>
            <td>name</td>
            <td>base price</td>
            <td>rating</td>
            <td>actions</td>
            <td></td>
            <td></td>

        </tr>

        <#list events as event>
            <tr>
                <td>${event.name}</td>
                <td>${event.basePrice}</td>
                <td>${event.rating}</td>

                <td><a href="/event/remove/${event.id}">remove</a></td>
                <td><a href="/event/update/${event.id}">update</a></td>
                <td><a href="/event/airDates/${event.id}">purchased tickets</a></td>

            </tr>
        </#list>
        </table>
    </div>

    <div class="col s6">
        <!--<a class="waves-effect waves-light btn-large" href="/event/add">add event</a>-->
        <a class="waves-effect waves-light btn-large" href="/event/add">add event</a>
        <a class="waves-effect waves-light btn-large" href="/">home page</a>
    </div>
</div>

</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>add event</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

        <meta name = "viewport" content = "width = device-width, initial-scale = 1">
        <link rel = "stylesheet"
              href = "https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel = "stylesheet"
              href = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
        <script type = "text/javascript"
                src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script src = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js">
        </script>

        <script>
            $(document).ready(function() {
                $('select').material_select();
            });
        </script>

</head>
<body>

<form action="/event/add" method="post" modelAttribute="event">

    <div class="row">
        <div class="input-field col s6">
            <input id="name" name="name" type="text">
            <label for="name">name</label>
        </div>
        <div class="input-field col s6">
            <input id="basePrice" name="basePrice" type="text">
            <label for="basePrice">base price</label>
        </div>

        <div class="input-field col s12">
            <select name="rating">
            <#list ratings as item>
                <option value="" disabled selected>Choose rating</option>
                <option value="${item}">${item}</option>
            </#list>
            </select>
            <label>Select event rating</label>
        </div>


    </div>

    <button class="btn waves-effect waves-light" type="submit" name="action">OK
        <i class="material-icons right"></i>
    </button>

    <form>


</body>
</html>
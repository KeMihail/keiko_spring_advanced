<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>book ticket</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">


</head>
<body>

<form action="/booking/tickets" method="post">

    <#list tickets as ticket>

        <p>
            <label>
                <input id="indeterminate-checkbox" type="checkbox" name="item" value="${ticket.id}"/>
                <span>${ticket}</span>
            </label>
        </p>
    </#list>
    <p>
        <input type="submit" name="submit">

</form>


</body>
</html>
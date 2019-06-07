<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>tickets list</title>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>

<div class="row">
    <div class="col s6">

        <table class="class=" highlight
        ">
        <table>
            <tr>
                <td>date time</td>
                <td>seat</td>
                <td>event</td>
                <#if viewAll>
                <td>actions</td>
                <td></td>
                 <td></td>
                </#if>
            </tr>

            <#list tickets as ticket>
                <tr>
                    <td>${ticket.dateTime}</td>
                    <td>${ticket.seat}</td>
                    <td>${ticket.event}</td>
                    
                    <#if viewAll>
                    <td></td>
                    <td><a href = "/ticket/remove/${ticket.id}">remove</td>
                    <td><a href = "/ticket/update/${ticket.id}">update</td>
                     </#if>
                    
                </tr>
            </#list>
        </table>
    </div>

    <div class="col s6">
     <#if viewAll>
        <a class="waves-effect waves-light btn-large" href="/ticket/add">add
            ticket</a>
            </#if> <a class="waves-effect waves-light btn-large" href="/">home
            page</a>
    </div>
</div>
</div>
</body>
</html>
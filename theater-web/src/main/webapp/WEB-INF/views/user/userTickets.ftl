<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>user tickets</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>
	<div class="row">
		<div class="col s6">

			<table class="class="highlight">
				<tr>
					<td>event</td>
					<td>date time</td>
					<td>seat</td>
				</tr>

				<#list tickets as ticket>
				<tr>
					<td>${ticket.event}</td>
					<td>${ticket.dateTime}</td>
					<td>${ticket.seat}</td>
				</tr>
				</#list>

			</table>
		</div>

		<div class="col s6">
			<a class="waves-effect waves-light btn-large" href="/user/usersList">BACK</a>
		</div>



	</div>
</body>
</html>
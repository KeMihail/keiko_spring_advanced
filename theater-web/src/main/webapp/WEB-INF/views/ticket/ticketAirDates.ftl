<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ticket air dates</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>


	<#list airDates as date>

	<div class="col s6">
		<a class="waves-effect waves-light btn-large"
			href="/booking/purchased/${date}">${date}</a>
	</div>

	</#list>

</body>
</html>
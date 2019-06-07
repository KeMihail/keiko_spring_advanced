<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>add ticket</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

<meta name="viewport" content="width = device-width, initial-scale = 1">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js">
	
</script>

<script>
	$(document).ready(function() {
		$('select').material_select();
	});
</script>

</head>
<body>

	<form action="/ticket/add" method="post" modelAttribute="ticket">
		<div class="row">
			<div class="input-field col s6">
				<input name="dateTime" type="datetime-local">
				<label for="dateTime">date and time</label>
			</div>
			<div class="input-field col s6">
				<input name="seat" type="text">
			<label for="seat">seat</label>
			</div>
			<div class="input-field col s6">

				<select name="event">
				<option value="" disabled selected>Choose event</option>
					<#list events ? keys as key>
					<option value="${key}">"${events[key]}"</option>
					</#list>
				</select>
			</div>
		</div>
		   <button class="btn waves-effect waves-light" type="submit" name="action">OK
        <i class="material-icons right"></i>
    </button>
	</form>

</body>
</html>
<!DOCTYPE html>
<html>
    <head>
        <title>Message</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
		<div align="center">
			<div align="center" style="width: 50%;">
				<h2>Job-App REST-Service Home</h2>
				<hr>
				<div align="left">
		        	<p style="font: 1.5em bold;">
		            	Message: ${message}
		        	</p>
		        	<#if time??>
		            	<p>Date and time: ${time}</p>
		        	</#if>
				</div>
			</div>
		</div>
    </body>
</html>
<!DOCTYPE html> 
<html lang="es">
	<body>
		<%	
			String respuesta = (String) request.getAttribute("respuesta");
			if(respuesta==null){
				respuesta = "";
			}
			%>
		<h2>Monitorizador de cambios en páginas</h2>
		<form method="post" action="hello">
		
			Rellene el formulario para comenzar la monitorización, una vez finalizado
			el plazo establecido se le enviará un correo con el informe detallado.<br/><hr>
		 
			Url acortada de la página a monitorizar <input type="text" name="url"><br/><hr>
			Frecuencia en minutos (minimo 5 minutos) <input type="number" name="freq"><br/><hr>
			Fecha final de monitorización (máximo 1 mes) <input type="date" name="date"><br/><hr>
			Hora final de monitorización (opcional) <input type="number" name="hour"><br/><hr>
			Email al que se enviará el informe <input type="text" name="email"><br/><hr>
			
			<br/>
			<button type="submit" value="Submit">Enviar</button>
			
		</form>
		
		<h4><%=respuesta%></h4>
	</body>
</html>
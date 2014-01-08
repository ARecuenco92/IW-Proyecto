<!DOCTYPE html> 
<html lang="es">
	<body>
		<%	
			String respuesta = (String) request.getAttribute("respuesta");
			if(respuesta==null){
				respuesta = "";
			}
			%>
		<h2>Monitorizador de cambios en p�ginas</h2>
		<form method="post" action="hello">
		
			Rellene el formulario para comenzar la monitorizaci�n, una vez finalizado
			el plazo establecido se le enviar� un correo con el informe detallado.<br/><hr>
		 
			Url acortada de la p�gina a monitorizar <input type="text" name="url"><br/><hr>
			Frecuencia en minutos (minimo 5 minutos) <input type="number" name="freq"><br/><hr>
			Fecha final de monitorizaci�n (m�ximo 1 mes) <input type="date" name="date"><br/><hr>
			Hora final de monitorizaci�n (opcional) <input type="number" name="hour"><br/><hr>
			Email al que se enviar� el informe <input type="text" name="email"><br/><hr>
			
			<br/>
			<button type="submit" value="Submit">Enviar</button>
			
		</form>
		
		<h4><%=respuesta%></h4>
	</body>
</html>
<html>
<html lang="es">
<meta charset="UTF-8">
<title>Monitorizador de Cambios</title>
<link rel="stylesheet" type="text/css" href="dist/css/bootstrap.min.css">
<style>
input{ max-width: 350px; }
</style>
</head>
<body style="margin: 0% 1%;">
		<%	
			String respuesta = (String) request.getAttribute("respuesta");
			if(respuesta==null){
				respuesta = "";
			}
		%>
<div class="page-header">
  <h1 style="margin-bottom:2px;">Monitorizador de cambios en páginas</h1>
</div>
<p style="margin-bottom:15px;">Rellene el formulario para comenzar la monitorización, una vez finalizado
 el plazo establecido se le enviará un correo con el informe detallado.<p/>
<form role="form" method="post" action="form" id="form" style="padding:1%; border: 1px #eee solid; border-radius:4px;">
<div class="form-group">
    <label for="url" class="col-sm-3">Url acortada a monitorizar</label>
    <input type="text" name="url" class="form-control" id="url" placeholder="Url Acortada" value="http://7iw.es/" required>
</div>
<div class="form-group">
    <label for="freq" class="col-sm-3">Frecuencia en minutos [5 - 300]</label>
    <input type="number" name="freq" class="form-control" id="freq" placeholder="Frecuencia Minutos" required>
</div>
<div class="form-group">
    <label for="date" class="col-sm-3">Fecha finalización (max. 1 Mes)</label>
    <input type="date" name="date" class="form-control" id="date" placeholder="Fecha Final" required>
</div>
<div class="form-group">
    <label for="hour" class="col-sm-3">Hora finalización (opcional)</label>
    <input type="number" name="hour" class="form-control" id="hour" placeholder="Hora Final">
</div>
<div class="form-group">
    <label for="email" class="col-sm-3">Email al que se enviará el informe</label>
    <input type="email" name="email" class="form-control" id="email" placeholder="Email Entrega" required>
</div>
<button id="submit" class="btn btn-primary" type="submit" value="Submit">Enviar Datos</button>
<h4 style="color:#f00"><%=respuesta%></h4>
</form>
<script>
	var submit = document.getElementById("submit");
	submit.onclick=function(){
		var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name", "dateDelay");
		input.setAttribute("value", new Date().getTimezoneOffset());
		document.getElementById("form").appendChild(input);
	};
</script>
</body>
</html>
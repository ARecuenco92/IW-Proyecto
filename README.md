IW-Proyecto
===========

la aplicación trata de un cliente y un servicio para monitorizar cambios en páginas web y generar un informe que se manda por correo.



Cliente:
Se trata de una página JSP con un formulario donde se fijan los parámetros de monitorización. Cuando se envía el formulario, un servlet comprueba los parámetros y llama al servicio (servidor).


Servicio:
El servicio monitoriza una web acortada dada, durante un tiempo dado, con una frecuencia dada y manda los resultados de la monitorización en formato pdf a un email dado.
El servicio se divide en los siguientes paquetes:
-	Database: contiene clases con métodos para acceder a la base de datos global y a nuestra base de datos local.
-	Mail: contiene una clase que se encarga de redactar el email y adjuntar el informe en pdf, así como de mandarlo a su respectivo destinatario.
-	Monitor: contiene clases que monitorizan una página web dada.
-	Pdf: contiene una clase que genera un informe en pdf con todos los cambios para una petición de monitorización.
-	Server: contiene clases que representan los objetos utilizados en la aplicación, y clases de acceso a la base de datos. Se sigue un patrón facade.
Se han utilizado librerías externas como itext, java mail, quartz, etc.

Base de datos local:
La base de datos local se utiliza para registrar la monitorización de las páginas. Cada fila de la tabla datos representa una petición de monitorización por parte del cliente. Cada fila de la tabla cambios contiene una monitorización de una página de la tabla datos en un momento concreto y se registra si ha habido cambio o no. 



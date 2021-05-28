# Autenticación de APIs basada en tokens con Spring y JWT

## Estado

Aceptado

## Contexto

Se necesitan cear tokens de acceso que nos permiten securizar las comunicaciones entre cliente y servidor

## Decisión

El uso  de Spring Security para poder proteger nuestra API permitiendo el acceso a los endpoints de la aplicación.
El uso del estándar JSON Web Token (JWT).

## Consecuencias

El cliente solo necesitará autenticarse con sus credenciales una sola vez. Durante este tiempo el servidor validará las credenciales y retornará al cliente un JSON web Token. Para las requests futuras el cliente usará este token para autenticarse sin necesidad de usar sus credenciales. Además , permitará el cifrado de las credenciales del cliente y provee un mecanismo de autenticación escalable , sin estado y desaclopado.


# Persistencia de los datos con Atlas MongoDB

## Estado

Aceptado

## Contexto

Se necesita manejar un esquema de datos altamente escalable y rapido.

## Decisión

El uso del modulo MongoDb perteneciente a Spring Data para manejar el acceso a la base de datos.

El uso de una base de datos NoSql en ese caso MongoDb la cual está desplegada en Atlas.


## Consecuencias

Los datos al almacenarse en una estructura parecida a Json permite que el flujo de estos mismos dentro de la aplicación no tenga mayores cambios en terminos de estructura, aparte esta nos ofrece varias
ventajas en terminos de atributos de calidad, por ejemplo al ser una base de datos distribuida puede escalar facilmente, por otra parte MongoDB permite tener cluster distribuidos. Esto nos permite mejorar la disponibilidad y la mejora en la velocidad de consulta al disminuir la latencia entre la base de datos y el servidor.

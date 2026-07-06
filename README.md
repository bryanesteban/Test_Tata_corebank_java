# Banquito - Documentación de Pruebas y Despliegue

Los archivos de base de datos y curl de Postman se encuentran en la dirección:

```
resource/liquidbase
```

También se puede usar la siguiente dirección. No olvidar cambiar el puerto si es necesario tomando en cuenta la disponibilidad del mismo y el archivo `application.properties`.

---

## Clientes API

### POST
```
http://localhost:8080/api/clientes
```

```json
{
  "clienteid":"100000",
  "identificacion": "1235763296",
  "nombre": "Jose Lema",
  "direccion": "Otavalo sn y principal",
  "telefono": "098254785",
  "contrasena": "1234",
  "estado": true
}
```

```json
{
  "clienteid":"100001",
  "identificacion": "17223465323",
  "nombre": "Marianela Montalvo",
  "direccion": "Amazonas y NNUU",
  "telefono": "097548965",
  "contrasena": "5678",
  "estado": true
}
```

```json
{
  "clienteid":"100002",
  "identificacion": "17523482735",
  "nombre": "Juan Osorio",
  "direccion": "13 junio y Equinoccial",
  "telefono": "098874587",
  "contrasena": "1245",
  "estado": "True"
}
```

```json
{
  "clienteid":"100003",
  "identificacion": "175234564635",
  "nombre": "Mateo Urrutia",
  "direccion": "Quito",
  "telefono": "0988456487",
  "contrasena": "123214",
  "estado": "True"
}
```

---

## Cuentas API

```
http://localhost:8080/api/cuentas
```

```json
{
  "numeroCuenta": "478758",
  "tipoCuenta": "Ahorro",
  "saldo": "2000",
  "estado": "True",
  "clienteId": "100000"
}
```

```json
{
  "numeroCuenta": "225487",
  "tipoCuenta": "Corriente",
  "saldo": "100",
  "estado": "True",
  "clienteId": "100001"
}
```

```json
{
  "numeroCuenta": "495878",
  "tipoCuenta": "Ahorros",
  "saldo": "0",
  "estado": "True",
  "clienteId": "100002"
}
```

```json
{
  "numeroCuenta": "496825",
  "tipoCuenta": "Ahorros",
  "saldo": "540",
  "estado": "True",
  "clienteId": "100001"
}
```

```json
{
  "numeroCuenta": "585545",
  "tipoCuenta": "Corriente",
  "saldo": "1000",
  "estado": "True",
  "clienteId": "100000"
}
```

---

## Movimientos API

```
http://localhost:8080/api/movimientos
```

```json
{
  "numerocuenta":"478758",
  "tipomovimiento":"RETIRO",
  "valor":"575"
}
```

```json
{
  "numerocuenta":"225487",
  "tipomovimiento":"DEPOSITO",
  "valor":"600"
}
```

```json
{
  "numerocuenta":"495878",
  "tipomovimiento":"DEPOSITO",
  "valor":"150"
}
```

```json
{
  "numerocuenta":"496825",
  "tipomovimiento":"RETIRO",
  "valor":"540"
}
```

---

### F3 - Saldo insuficiente prueba

```json
{
  "numerocuenta":"478758",
  "tipomovimiento":"RETIRO",
  "valor":"1500"
}
```

---

## F4 - Reporte por fecha

```
GET http://localhost:8080/api/movimientos/reportes?fechaInicio=01/01/2024&fechaFin=31/12/2024
```

---

## F7 - Ejecutar en contenedor

### Dockerfile

```
FROM openjdk:21-jdk

WORKDIR /app

COPY banquito-0.0.1-SNAPSHOT.jar /app/banquito.jar

EXPOSE 8080
EXPOSE 3306

ENTRYPOINT ["java", "-jar", "banquito.jar"]
```

---

### Comandos Docker

```bash
docker build -t banquito-app .
docker run -d -p 8080:8080 --name banquito-container banquito-app
docker ps
```

---

## Recomendaciones

- Se pudo haber encriptado la contraseña con bcrypt para mayor seguridad.
- Se podría agregar Kafka para manejo de mensajería entre servicios.
- Se podría implementar WebFlux para manejo asincrónico de transacciones.
- Para Kubernetes se podría agregar el archivo de deployment del contenedor.
- Se pudo haber implementado OpenAPI para documentación de DTOs.

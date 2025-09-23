# Taller-05-AREP

## Property-Manager

### Sistema CRUD para Propiedades 🏡

Este proyecto implementa un sistema CRUD (Create, Read, Update, Delete) para administrar propiedades inmobiliarias. Con esta aplicación podrás:

Registrar nuevas propiedades.

Consultar el listado completo o detalles de una propiedad específica.

Editar datos de propiedades existentes.

Eliminar registros de propiedades.

Está desarrollado con Spring Boot en el backend, MySQL como base de datos y una interfaz sencilla en HTML + JavaScript. El despliegue se realiza en AWS, utilizando instancias EC2 y contenedores Docker.

### Videos:

(https://youtu.be/IKl8q8Ex4_U) 


### 🧩 Arquitectura del Sistema

La aplicación se compone de tres capas principales:

1. Backend (API REST)

    - Desarrollado con Spring Boot.

    - Ofrece endpoints CRUD en el puerto 8080.

    - Contiene la lógica de negocio y se conecta con la base de datos.

2. Frontend

    - Interfaz construida con HTML, CSS y JavaScript.

    - Emplea Fetch API para comunicarse con el backend.

    - Permite al usuario gestionar las propiedades.

3. Base de Datos

    - MySQL en contenedor Docker.

    - Almacena información de propiedades: id, dirección, precio, tamaño y descripción.

    - Expone el puerto 3306.

### 🔑 Flujo de Interacción 

- El frontend envía peticiones HTTP (GET, POST, PUT, DELETE) al backend. 
- El backend procesa y consulta la base de datos, devolviendo las respuestas al cliente.
 
### 🖥️ Diseño de Clases 
 
Principales componentes del backend:

- Property: entidad que representa una propiedad.

- PropertyRepository: interfaz de acceso a datos que extiende JpaRepository.

- PropertyService: contiene la lógica de negocio para las operaciones CRUD.

- PropertyController: expone los endpoints REST.

```
src/
  main/
    java/
      com/example/propertymanager/
          Property.java
          PropertyRepository.java
          PropertyService.java
          PropertyController.java
          Application.java
    resources/
        static/
            index.html
            styles.css
            app.js
        application.properties
  test/
    java/
Dockerfile
pom.xml
README.md

   ```

### ⚙️ Instalación Local

1. Clona el repositorio:

    ```
    git clone https://github.com/juanescan/Taller-05-AREP.git
    
    ```
2. Compila el proyecto:

    ```
    mvn clean install
    
    ```
3. Ejecuta:

    ```
    mvn spring-boot:run

    ```

### ☁️ Despliegue en AWS (Resumen)

1. Base de datos MySQL

- Lanza una instancia EC2, instala Docker y ejecuta:

    ```
    docker run --name property-db \
    -e MYSQL_ROOT_PASSWORD=root \
    -e MYSQL_DATABASE=property_db \
    -p 3306:3306 -d mysql:latest

    ```

2. Backend 
  
- Empaqueta la app y crea la imagen:  

    ```
    mvn clean package
    docker build -t property-manager .
    docker tag property-manager juanescan/taller5
    docker push juanescan/taller5

    ``` 
- En otra instancia EC2:
  
   ```
    docker pull juanescan/taller5
    docker run -d -p 8080:8080 --name property-manager juanescan/taller5
     
    ```  
3. Reglas de seguridad
 
- Permite el puerto 3306 para MySQL (solo desde el backend).

- Permite el puerto 8080 para el backend.

### APP

![app](/images/1.png)

![app](/images/2.png)
  
### 🛠️ Tecnologías Utilizadas

- Java

- Spring Boot

- Maven

- MySQL 

- Docker

- AWS EC2

### 👤 Autor

- Juan Esteban Cancelado Sanchez - *AREP* *Taller 5* - [juanescan](https://github.com/juanescan)
# IETI Laboratorio 6

Daniel Felipe Walteros Trujillo

[![Deploy to Azure](https://aka.ms/deploytoazurebutton)](http://walteros-taskplanner-back.westus.azurecontainer.io:8080/)

# Endpoints

1. Consultar todos los usuarios.

   ```bash
    GET: <URL>/users
   ```

2. Consultar un usuario por su Id.

   ```bash
    GET: <URL>/users/<Id>
   ```

3. Crear un nuevo usuario.

   ```bash
    POST: <URL>/users
    Body: {
        "email":"<email>",
        "name":"<name>",
        "password":"<password>"
    }
   ```

4. Actualizar la información de un usuario.

   ```bash
    PUT: <URL>/users
    Body: {
        "id":<id>,
        "email":"<email>",
        "name":"<name>",
        "password":"<password>"
    }
   ```

5. Eliminar un usuario con un Id.

   ```bash
    DELETE: <URL>/users/<Id>
   ```

# Uso Local Backend

1. Clonar el repositorio.

2. Compilar el proyecto con el comando `gradle build`.

3. Generar la imagen de docker con el comando `docker build -t <nombre de la imagen> . `, puedes poner el nombre de la imagen que quieras.

4. Ejecutar una instancia con la imagen usando el comando `docker run -p <puerto>:8080 <nombre de la imagen> `, donde el puerto puede ser el que tú quieras.
   Este comando despliega el backend de la aplicación por lo tanto puedes dirigirte a la URL `http://localhost:<puerto>/users`, este endpoint debe mostrarte los usuarios cargados en la aplicación de la siguiente forma:
   
   ![](img/Response.PNG)
   
# Uso Local Con Interfaz Gráfica

1. Clonar el repositorio.

2. Dirigirte a la carpeta `FrontComponent`.

3. Instalar las dependencias de la aplicación de react con el comando `npm install`.

4. Ejecutar la aplicación con el comando `npm start`, como resultado se debe ver la información del back de la aplicación de la siguiente forma:

   ![](img/Front.PNG)

# Uso Remoto Con Dockerhub

1. Ejecuta la imagen del DockerHub con el comando `docker run -d -p <puerto>:8080 --name <nombre de la imagen> silenrate/ieti-backend`.

2. Dirigirte a la URL `http://localhost:<puerto>/users`, este endpoint debe mostrarte los usuarios cargados en la Aplicación.

# Uso Remoto En Azure

1. Dirigirte a la URL `http://walteros-taskplanner-back.westus.azurecontainer.io:8080/users`, este endpoint debe mostrarte los usuarios cargados en la Aplicación.

# Código de Honor
------
Debes seguir el Código de honor del ingeniero de sistemas para defender el estándar de integridad académica de la ECI:

- Tus respuestas a tareas, cuestionarios y exámenes deben ser tu propio trabajo (excepto para las tareas que permiten explícitamente la colaboración).

- No puedes compartir tus soluciones de tareas, cuestionarios o exámenes con otra persona a menos que el instructor lo permita explícitamente. Esto incluye cualquier cosa escrita por ti, como también cualquier solución oficial proporcionada por el docente o el monitor del curso.

- No puedes participar en otras actividades que mejorarán de manera deshonesta tus resultados o que mejorarán de manera deshonesta o dañarán los resultados de otras personas.

# 2.1 Task Planner API

### Prerequisites
- Install the JDK. It is suggested to use the version 1.8 or newer
- Gradle as a build tool. See: https://gradle.org/install/
- Install the docker CLI. See: https://docs.docker.com/get-docker/
- It may be helpful to install an IDE (Eclipse, IntelliJ, VS Code, ...)

### Part 1: Task Planner API V1
1. Create a new Spring Boot project using the Spring Initializr (https://start.spring.io/). Include the *Spring Web* dependency.

2. Create a Plain Old Java Object (POJO) to map the model for a *User* of the Task Planner PWA.

3. Create an interface to define the contract of the *UserService* class. This service will handle all users' functionality:
    ```java
   public interface UserService {
        List<User> getAll();
        
        User getById(String userId);
        
        User create(User user);
        
        User update(User user);
        
        void remove(String userId);
    }
    ```

4. Implement the UserService contract. For now, it is enough to have dummy implementations returning mock responses.

5. Configure your application so that you can inject the services implementations anywhere in your project. Use the annotations *@Service* and *@Autowired*. Check the official documentation and the following example as guidance:
   https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-spring-beans-and-dependency-injection.html

6. Implement a REST controller named *UserController* to expose the services. Use the following resource as example:
   https://spring.io/guides/tutorials/rest/

7. Inject the *UserService* on the controller and redirect the requests to the service.

8. Test your application locally: Use for example the __bootRun__ Gradle task to run the embedded server. Then try to reach your endpoints.

### Part 2: Containerize and deploy your backend

1. Create a *dockerhub* account if you don't have one. Follow the steps here: https://docs.docker.com/docker-hub/

2. Read about docker. You can use the following quickstart guide: https://docs.docker.com/get-started/. Then, create a file named *Dockerfile* (without any extension) at the root folder of your java project. Add the following contents:
    ```Dockerfile
    FROM adoptopenjdk/openjdk11:alpine
    RUN addgroup -S spring && adduser -S spring -G spring
    USER spring:spring
    EXPOSE 8080
    ARG OUTPUT_FOLDER=build/libs/
    COPY ${OUTPUT_FOLDER} /app/lib
    WORKDIR /app/lib
    ENTRYPOINT ["java","-jar","task-planner-0.0.1-SNAPSHOT.jar"]
    ```
__Note 1:__ In case you are using Java 1.8, you may need to use a different base image: ```FROM openjdk:8-jdk-alpine```

__Note 2:__ You may need to modify the last argument in the __ENTRYPOINT__ according to the name and version of your application

3. Assemble your artifact and make sure that a jar file was generated under the *build/libs* folder.

    ```bash
        gradle build
    ```

4. Build the container image using the following command. Replace __<dockerhub_user>__ with your dockerhub user account. Don´t forget the period at the end of the command. It indicates the path where the Dockerfile and the image context can be found. Remember to run this command at the root level of the project.

    ```bash
    docker build -t <dockerhub_user>/ieti-backend .
    ```

5. Make sure you can run the container locally and it works as expected.

    ```bash
    docker run -p 8080:8080 <dockerhub_user>/ieti-backend
    ```

6. Push the image to the dockerhub registry:

    ```bash
    docker push <dockerhub_user>/ieti-backend
    ```

7. Deploy your container to Azure. Use the steps in the following guide but referencing the image you just created:
   https://docs.microsoft.com/en-us/azure/container-instances/container-instances-quickstart-portal. Have in mind the port you are using for your app (default is 8080) and configur it in the networking section.

8. Check you can reach the endpoints through the FQDN. The URL should be similar to this: http://ieti-backend.eastus.azurecontainer.io:8080/

__Note:__ In case you don't have access to an Azure account, read the following tutorial to deploy containers to heroku:

https://devcenter.heroku.com/articles/container-registry-and-runtime

### Part 3: Consume the Task Planner API from a React JS App

1. Implement the lifecycle method *componentDidMount()* on a React component that displays the Users list. Inside the method use the function fetch to retrieve the User list from the API. For now, returning some sample data from the backend would be enough.
    ```javascript
    class App extends Component {
    
        constructor(props) {
            super(props);
            this.state = {
                userList: [],
            };
        }

        componentDidMount() {
            fetch('REPLACE-API-URL')
                .then(response => response.json())
                .then(data => {
                    let userList = [];
                    data.items.forEach(function (user) {
                        usersList.push({
                           //Implement this part
                        })
    
                    });
                    this.setState({userList: userList});
                });
        }

        render() {
            return (
                <div>
                    <!-- Implement a React compontent to render the list -->
                    <UserList usersList={this.state.usersList}/>
                </div>
            );
        }
    }

    export default App;
    ```

__TIP__ In order to test your application I recommend Firefox browser. You must install the following plugin to avoid CORS validation restriction:

https://addons.mozilla.org/en-US/firefox/addon/cors-everywhere/

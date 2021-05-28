# IETI Laboratorio 10

Daniel Felipe Walteros Trujillo

## Prerequisitos

- Java Version 8 o superior.
- Gradle Versión 5 o superior.
- NodeJS Versión 12 o superior.
- NPM Versión 6 o superior.

## Uso

1. Clonar el repositorio.

2. Incluir la URL de la MongoDB en un archivo llamado `application.yml` bajo las indicaciones del enunciado encontradas abajo.

2. Desde tu IDE preferido ejecutar la función main de la clase `Application` ubicada en `src/main/java/eci/ieti`.

3. Desde tu consola de comandos favorita compila y ejecuta el proyecto de la carpeta `FrontComponent` con el comando `npm install` y el comando `npm start`.

# Código de Honor
------
Debes seguir el Código de honor del ingeniero de sistemas para defender el estándar de integridad académica de la ECI:

- Tus respuestas a tareas, cuestionarios y exámenes deben ser tu propio trabajo (excepto para las tareas que permiten explícitamente la colaboración).

- No puedes compartir tus soluciones de tareas, cuestionarios o exámenes con otra persona a menos que el instructor lo permita explícitamente. Esto incluye cualquier cosa escrita por ti, como también cualquier solución oficial proporcionada por el docente o el monitor del curso.

- No puedes participar en otras actividades que mejorarán de manera deshonesta tus resultados o que mejorarán de manera deshonesta o dañarán los resultados de otras personas.


# 2.4 MongoDB Files API
Create Spring Boot API that allows uploading and serving files.


## Part 1: Implement a File uploader API

1. Clone this project and change the Database configuration with your own connection string in the following file:
        
    *application.yml*:
    ``` yaml
    spring:
      data:
        mongodb:
          uri: mongodb+srv://<username>:<password>@ieti-dzkk5.mongodb.net/test?retryWrites=true&w=majority 
    ```

*Note:* Don't forget to add your IP address in the IP Whitelist of your MongoDb Atlas cluster before attempting to run your application. 

2. Run the project.

3. Go to your database and verify that the file was created under a fs.files and fs.chunks documents. 

4. Go to the *RESTController* class and inject the *GridFsTemplate* bean by using the *@Autowired* annotation.

5. Implement the *getFileByName* method:

    * Find the file with the following code:
        ````Java
          GridFSFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(filename)));
        ````
    * If the file does not exist return a Not Found HTTP status response:
    
        ````Java
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ````
    * If the file exist return the resource:
        ````Java
                GridFsResource resource = gridFsTemplate.getResource(file.getFilename());
                return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(resource.getContentType()))
                    .body(new InputStreamResource(resource.getInputStream()));
        ````    
    
5. Verify that the *getFileByName* endpoint works and it does returns the *lion.jpeg* file created on step 2 when requested by name.


6. Implement the *handleFileUpload* method. Make sure you return the static url to access the uploaded file by using the *getFileByName* endpoint.

    ````Java
       //Stores the file into MongoDB
        gridFsTemplate.store(file.getInputStream(), fileName, file.getContentType());
    ````

## Part 2: Integrate file uploader with React JS project

1. Using the TODO List application implemented in previous labs, add the following input to the Todo form:

    ````Javascript
        <input type="file" id="file" onChange={this.handleInputChange}/>
    ````
    
2. Implement and bind the *handleInputChange* method:

    ````Javascript
         handleInputChange(e) {
                this.setState({
                    file: e.target.files[0]
                });                
            }
    ````

3. Change the *handleSubmit* method so it uploads the file first:

    ````Javascript
    
            let data = new FormData();
            data.append('file', this.state.file);
    
            this.axios.post('files', data)
                .then(function (response) {
                    console.log("file uploaded!", data);
            })
            .catch(function (error) {
                console.log("failed file upload", error);
            });
    
    ```` 
4. Run your React project and verify that the file uploader works.


## Part 3: Upgrade your Todo to accept files

1. Inject the *TodoRepository* into the *RESTController* using *@Autowired* annotation.

2. Implement the *createTodo* and *getTodoList* methods of the *RESTController*.

3. Modify the *handleSubmit* method so it does call the API to create the *Todo* entry on the server and database:

    * Make asynchronous calls to upload file to the server
    * Once the file upload promise is fulfilled, then save the Todo entry using the *POST* method of the API.
    * Remember to save the *this* context into a variable to use it into the nested scopes!
   
4. Modify the *Todo* component on your React project so that it displays the Todo image:

    ````Javascript
          <td>{this.props.fileUrl ? <img src={this.props.fileUrl} /> : <div/>}</td>
    ```` 

5. If you have not done it yet, implement and call the method to load the Todo list from the server:

    ```javascript
         loadDataFromServer() {
        
                let that = this;
        
                this.axios.get("todo").then(function (response) {
                    console.log("This is my todolist:  ", response.data);
                    that.setState({items: response.data})
                })
                    .catch(function (error) {
                        console.log(error);
                    });
            }

    ```    
    6. Implement the logic for the following scenario: if the file is not an image but a pdf file, then a file icon appears on the Todo component. When the file icon is clicked then the user gets redirected to the download page.
    
7. Verify that the project works as expected.

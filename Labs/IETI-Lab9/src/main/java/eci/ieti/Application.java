package eci.ieti;

import eci.ieti.data.CustomerRepository;
import eci.ieti.data.ProductRepository;
import eci.ieti.data.TodoRepository;
import eci.ieti.data.UserRepository;
import eci.ieti.data.model.Customer;
import eci.ieti.data.model.Product;
import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private static final long DAY_IN_MILLISECONDS = 86400000;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        customerRepository.deleteAll();

        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Marley"));
        customerRepository.save(new Customer("Jimmy", "Page"));
        customerRepository.save(new Customer("Freddy", "Mercury"));
        customerRepository.save(new Customer("Michael", "Jackson"));

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");

        customerRepository.findAll().forEach(System.out::println);
        System.out.println();

        productRepository.deleteAll();

        productRepository.save(new Product(1L, "Samsung S8", "All new mobile phone Samsung S8"));
        productRepository.save(new Product(2L, "Samsung S8 plus", "All new mobile phone Samsung S8 plus"));
        productRepository.save(new Product(3L, "Samsung S9", "All new mobile phone Samsung S9"));
        productRepository.save(new Product(4L, "Samsung S9 plus", "All new mobile phone Samsung S9 plus"));
        productRepository.save(new Product(5L, "Samsung S10", "All new mobile phone Samsung S10"));
        productRepository.save(new Product(6L, "Samsung S10 plus", "All new mobile phone Samsung S10 plus"));
        productRepository.save(new Product(7L, "Samsung S20", "All new mobile phone Samsung S20"));
        productRepository.save(new Product(8L, "Samsung S20 plus", "All new mobile phone Samsung S20 plus"));
        productRepository.save(new Product(9L, "Samsung S20 ultra", "All new mobile phone Samsung S20 ultra"));

        System.out.println("Paginated search of products by criteria:");
        System.out.println("-------------------------------");

        productRepository.findByDescriptionContaining("plus", PageRequest.of(0, 2)).stream()
                .forEach(System.out::println);

        System.out.println();

        System.out.println("Products with the word plus in their description:");
        System.out.println("-------------------------------");
        productRepository.findByDescriptionContaining("plus")
                .forEach(System.out::println);

        System.out.println();

        populateUsers();

        populateTodos();

        System.out.println("Todos with charles@natural.com as their responsible with pagination");
        System.out.println("-------------------------------");

        todoRepository.findByResponsible("charles@natural.com", PageRequest.of(0, 2)).stream()
                .forEach(System.out::println);

        System.out.println();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        System.out.println("Queries Implementation");

        queriesImplementation(mongoOperation);

        derivedQueryMethodsImplementation();

    }

    private void queriesImplementation(MongoOperations mongoOperation) {
        System.out.println("Todos where the dueDate has expired");
        System.out.println("-------------------------------");

        Query query = new Query();
        query.addCriteria(Criteria.where("dueDate").lt(new Date()));

        mongoOperation.find(query, Todo.class).forEach(System.out::println);

        System.out.println("Todos that are assigned to given user and have priority greater equal to 5");
        System.out.println("-------------------------------");

        query = new Query();
        query.addCriteria(Criteria.where("responsible").is("charles@natural.com").and("priority").gte(5));

        mongoOperation.find(query, Todo.class).forEach(System.out::println);

        System.out.println();

        System.out.println("Users that have assigned more than 2 Todos.");
        System.out.println("-------------------------------");

        query = new Query();
        query.addCriteria(Criteria.where("todosAmount").gt(2));

        mongoOperation.find(query, User.class).forEach(System.out::println);

        System.out.println();

        System.out.println("Todos that contains a description with a length greater than 30 characters");
        System.out.println("-------------------------------");

        query = new Query();
        query.addCriteria(Criteria.where("description").regex("^.{30,}$"));

        mongoOperation.find(query, Todo.class).forEach(System.out::println);

        System.out.println();
    }

    private void derivedQueryMethodsImplementation() {
        System.out.println("Todos where the dueDate has expired");
        System.out.println("-------------------------------");

        todoRepository.findByDueDateBefore(new Date())
                .forEach(System.out::println);

        System.out.println("Todos that are assigned to given user and have priority greater equal to 5");
        System.out.println("-------------------------------");

        todoRepository.findByResponsibleEqualsAndPriorityIsGreaterThanEqual("charles@natural.com", 5)
                .forEach(System.out::println);

        System.out.println();

        System.out.println("Users that have assigned more than 2 Todos.");
        System.out.println("-------------------------------");

        userRepository.findByTodosAmountIsGreaterThan(2).forEach(System.out::println);

        System.out.println();

        System.out.println("Todos that contains a description with a length greater than 30 characters");
        System.out.println("-------------------------------");

        todoRepository.findByDescriptionMatchesRegex("^.{30,}$").forEach(System.out::println);

        System.out.println();
    }

    private void populateTodos() {

        Date date = new Date();

        Date actualDate = new Date(date.getTime() + (2 * DAY_IN_MILLISECONDS));

        Date oldDate = new Date(date.getTime() - (5 * DAY_IN_MILLISECONDS));

        todoRepository.deleteAll();

        addTodo(new Todo("Travel to Galapagos", 10, actualDate, "charles@natural.com", "pending"));
        addTodo(new Todo("Travel to England", 8, oldDate, "charles@natural.com", "pending"));
        addTodo(new Todo("Write the origin of the species", 5, new Date(), "charles@natural.com", "pending"));
        addTodo(new Todo("Write war and peace", 7, actualDate, "leont@rusia.com", "pending"));
        addTodo(new Todo("Eat hamburger", 1, oldDate, "charles@natural.com", "pending"));

        addTodo(new Todo("Travel to Galapagos", 10, new Date(), "daniel@gmail.com", "pending"));
        addTodo(new Todo("Travel to England", 8, actualDate, "daniel@gmail.com", "pending"));
        addTodo(new Todo("Write the origin of the species", 6, actualDate, "daniel@gmail.com", "pending"));
        addTodo(new Todo("Write war and peace", 7, oldDate, "pepe@gmail.com", "pending"));
        addTodo(new Todo("Eat hamburger", 1, new Date(), "charles@natural.com", "pending"));

        addTodo(new Todo("Travel to Galapagos", 10, oldDate, "carla@gmail.com", "pending"));
        addTodo(new Todo("Travel to England", 8, oldDate, "carla@gmail.com", "pending"));
        addTodo(new Todo("Write the origin of the species", 6, oldDate, "carla@gmail.com", "pending"));
        addTodo(new Todo("Write war and peace", 7, actualDate, "roberto@gmail.com", "pending"));
        addTodo(new Todo("Eat hamburger", 1, actualDate, "charles@natural.com", "pending"));

        addTodo(new Todo("Travel to Galapagos", 10, new Date(), "andrea@gmail.com", "pending"));
        addTodo(new Todo("Travel to England", 8, actualDate, "andrea@gmail.com", "pending"));
        addTodo(new Todo("Write the origin of the species", 6, oldDate, "andrea@gmail.com", "pending"));
        addTodo(new Todo("Write war and peace", 7, actualDate, "rodolfo@gmail.com", "pending"));
        addTodo(new Todo("Eat hamburger", 1, oldDate, "charles@natural.com", "pending"));

        addTodo(new Todo("Travel to Galapagos", 10, actualDate, "mateo@gmail.com", "pending"));
        addTodo(new Todo("Travel to England", 8, oldDate, "mateo@gmail.com", "pending"));
        addTodo(new Todo("Write the origin of the species", 6, new Date(), "mateo@gmail.com", "pending"));
        addTodo(new Todo("Write war and peace", 7, actualDate, "camila@gmail.com", "pending"));
        addTodo(new Todo("Eat hamburger", 1, oldDate, "charles@natural.com", "pending"));
    }

    private void populateUsers() {
        userRepository.deleteAll();
        userRepository.save(new User("Daniel Walteros", "daniel@gmail.com"));
        userRepository.save(new User("Charles Darwin", "charles@natural.com"));
        userRepository.save(new User("Leon Tolstoi", "leont@rusia.com"));
        userRepository.save(new User("Pepe Gomez", "pepe@gmail.com"));
        userRepository.save(new User("Carla Rodriguez", "carla@gmail.com"));
        userRepository.save(new User("Roberto Gonzalez", "roberto@gmail.com"));
        userRepository.save(new User("Andrea Rodriguez", "andrea@gmail.com"));
        userRepository.save(new User("Rodolfo Rodriguez", "rodolfo@gmail.com"));
        userRepository.save(new User("Mateo Gonzalez", "mateo@gmail.com"));
        userRepository.save(new User("Camila Trujillo", "camila@gmail.com"));
    }

    private void addTodo(Todo todo) {
        todoRepository.save(todo);
        User user = userRepository.findByEmail(todo.getResponsible());
        if (user != null) {
            user.increaseTodoAmount();
            userRepository.save(user);
        }
    }

}
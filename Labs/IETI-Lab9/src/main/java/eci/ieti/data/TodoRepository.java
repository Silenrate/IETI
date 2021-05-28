package eci.ieti.data;

import eci.ieti.data.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, String> {

    Page<Todo> findByResponsible(String responsible, Pageable pageable);

    List<Todo> findByDueDateBefore(Date dueDate);

    List<Todo> findByResponsibleEqualsAndPriorityIsGreaterThanEqual(String responsible, int priority);

    List<Todo> findByDescriptionMatchesRegex(String regex);
}

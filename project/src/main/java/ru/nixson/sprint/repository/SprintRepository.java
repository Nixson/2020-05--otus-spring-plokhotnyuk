package ru.nixson.sprint.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nixson.sprint.domain.Sprint;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(path = "sprint")
public interface SprintRepository extends CrudRepository<Sprint,Long> {

    List<Sprint> findByCommand(Long command);

    @Query(value = "SELECT * from sprint where command  = ?1 and startday <= ?2 and endday >= ?2",nativeQuery = true)
    Sprint findActive(@Param("command") Long command, @Param("day") Date day);
}

package ru.nixson.sprint.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nixson.sprint.domain.TaskBurning;
import ru.nixson.sprint.dto.BurnDownDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RepositoryRestResource(path = "burn")
public interface TaskBurningRepository extends CrudRepository<TaskBurning,Long> {
    List<TaskBurning>  findByTaskIn(List<Long> ids);

    //@Query(value = "select extract(year from bdate) as yyyy, extract(month from bdate) as mm, extract(day from bdate) dd, sum(dtime) dtime from taskburning where task in (?) and bdate between ? and ? group by 1,2,3", nativeQuery = true)
    List<TaskBurning> findByTaskInAndBdateLessThanEqualAndBdateGreaterThanEqual(List<Long> tasks, Date startday, Date andday);
    List<TaskBurning> findByTaskInAndBdateGreaterThanEqualAndBdateLessThanEqual(List<Long> tasks, Date startday, Date andday);
}

package sn.ashia.projekt.componentactivity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentActivityRepository extends JpaRepository<ComponentActivity, Long> {
    List<ComponentActivity> findByComponent_Id(long componentId);
}

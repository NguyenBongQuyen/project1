package jmaster.io.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer>{
	@Query("SELECT d FROM Department d WHERE d.name LIKE :x")
	Page<Department> searchByName(@Param("x") String name, Pageable pageable);
	
}

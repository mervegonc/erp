package com.project.erp.repository.company;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erp.entity.company.Department;
import com.project.erp.entity.company.Position;

public interface PositionRepository  extends JpaRepository<Position, Long>{
   Optional<Position> findByNameAndDepartment(String name, Department department);
}

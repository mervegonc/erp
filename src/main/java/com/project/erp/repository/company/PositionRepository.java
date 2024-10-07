package com.project.erp.repository.company;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erp.entity.company.Position;

public interface PositionRepository  extends JpaRepository<Position, Long>{
   
}

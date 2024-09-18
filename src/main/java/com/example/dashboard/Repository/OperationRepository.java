package com.example.dashboard.Repository;

import com.example.dashboard.Entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OperationRepository extends JpaRepository<OperationEntity,Long> {

}

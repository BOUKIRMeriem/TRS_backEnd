package com.example.dashboard.Repository;

import com.example.dashboard.Entity.MachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MachineRepository extends JpaRepository<MachineEntity,Long> {

}

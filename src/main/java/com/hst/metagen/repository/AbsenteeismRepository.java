package com.hst.metagen.repository;

import com.hst.metagen.entity.Absenteeism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenteeismRepository extends JpaRepository<Absenteeism,Long> {
}

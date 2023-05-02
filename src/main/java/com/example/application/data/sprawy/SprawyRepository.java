package com.example.application.data.sprawy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SprawyRepository extends JpaRepository<Sprawy, Long>, JpaSpecificationExecutor<Sprawy> {

    Sprawy findAllByNrSprawy(Long nrSprawy);
    List<Sprawy> findAll();
    Sprawy findAllByData(Date data);
    Optional<Sprawy> findById(Long id);

}

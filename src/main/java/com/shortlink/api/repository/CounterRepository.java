package com.shortlink.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shortlink.api.model.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long>{

}

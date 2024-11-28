package com.alesh.Dispatch.repository;

import com.alesh.Dispatch.model.Box;
import com.alesh.Dispatch.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends JpaRepository <Box, String> {
    List<Box> findByState(State state);
}

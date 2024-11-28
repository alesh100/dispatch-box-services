package com.alesh.Dispatch.repository;

import com.alesh.Dispatch.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByBoxTxref (String txref);
}

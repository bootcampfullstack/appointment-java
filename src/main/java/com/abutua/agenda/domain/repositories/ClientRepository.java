package com.abutua.agenda.domain.repositories;




import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Lock;

import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.entities.Professional;

import jakarta.persistence.LockModeType;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // @Query(value = "SELECT * FROM TBL_CLIENT AS C JOIN TBL_PERSON AS P ON c.person_id = p.id", nativeQuery = true)
    // public List<Client> myFindAll();

    // @Query(value = "SELECT * FROM TBL_CLIENT AS C JOIN TBL_PERSON AS P ON c.person_id = p.id WHERE UPPER(P.name) LIKE  %:name% ", nativeQuery = true)
    // public List<Client> myFindAll(@Param("name") String name);

    Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);
 



}

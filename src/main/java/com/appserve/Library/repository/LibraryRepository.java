package com.appserve.Library.repository;

import com.appserve.Library.entity.LibraryCard;
import com.appserve.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryCard, Long> {
    LibraryCard findByUserId(User userId);
}

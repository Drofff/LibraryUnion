package com.appserve.Library.repository;

import com.appserve.Library.entity.Library;
import com.appserve.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryAccountRepository extends JpaRepository<Library, Long> {
    Library findByAccount(User account);
}

package com.appserve.Library.repository;

import com.appserve.Library.entity.Book;
import com.appserve.Library.entity.LibraryCard;
import com.appserve.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLibraryOwner(User user);
    List<Book> findByHolderId(LibraryCard libraryCard);

    @Query(value = "SELECT * FROM book WHERE name LIKE CONCAT('%', :query, '%') LIMIT 3", nativeQuery = true)
    List<Book> searchByName(@Param("query") String name);

    @Query(value = "SELECT * FROM book WHERE name LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<Book> search(@Param("query") String name);
}

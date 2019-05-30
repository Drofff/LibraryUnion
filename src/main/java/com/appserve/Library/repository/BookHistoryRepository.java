package com.appserve.Library.repository;

import com.appserve.Library.entity.Book;
import com.appserve.Library.entity.BookHistory;
import com.appserve.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookHistoryRepository extends JpaRepository<BookHistory, Long> {
    List<BookHistory> findByBookId(Book book);
    List<BookHistory> findByHolderId(User userId);
}

package com.accountbook.infrastrcture;

import com.accountbook.domain.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
    List<AccountBook> findByUserIdAndIsDelete(Long userId, boolean isDelete);
    Optional<AccountBook> findByIdAndIsDelete(Long accountBookId, boolean isDelete);
}

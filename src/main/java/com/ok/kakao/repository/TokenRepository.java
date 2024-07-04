package com.ok.kakao.repository;

import com.ok.kakao.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "select * from Token order by id desc limit 1", nativeQuery = true)
    Token getToken();
}

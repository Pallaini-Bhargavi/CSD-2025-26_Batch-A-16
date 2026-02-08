package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ResetPasswordRequest;

@Repository
public interface ResetPasswordRequestRepository
        extends JpaRepository<ResetPasswordRequest, Long> {

    List<ResetPasswordRequest> findByStatus(String status);

    Optional<ResetPasswordRequest>
    findTopByEmailAndStatusOrderByCreatedAtDesc(String email, String status);

    Optional<ResetPasswordRequest>
    findTopByEmailOrderByCreatedAtDesc(String email);
    boolean existsByEmailAndStatus(String email, String status);
    void deleteByEmail(String email);
    boolean existsByEmail(String email);

    List<ResetPasswordRequest> findByStatusIsNull();

}

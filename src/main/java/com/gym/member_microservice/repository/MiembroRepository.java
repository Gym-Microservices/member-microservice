package com.gym.member_microservice.repository;


import com.gym.member_microservice.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiembroRepository extends JpaRepository<Miembro, Long> {
}

package com.alissonpedrina.consumer.repo;

import com.alissonpedrina.consumer.domain.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}

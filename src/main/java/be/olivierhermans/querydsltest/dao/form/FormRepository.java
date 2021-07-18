package be.olivierhermans.querydsltest.dao.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FormRepository extends JpaRepository<FormEntity, Long> {
}

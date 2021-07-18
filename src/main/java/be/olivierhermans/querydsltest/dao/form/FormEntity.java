package be.olivierhermans.querydsltest.dao.form;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "form")
public class FormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;
    private Long clientId;
    private OffsetDateTime creationTms;
}

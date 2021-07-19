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
    @Column(name = "form_id")
    private Long formId;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "creation_tms")
    private OffsetDateTime creationTms;
}

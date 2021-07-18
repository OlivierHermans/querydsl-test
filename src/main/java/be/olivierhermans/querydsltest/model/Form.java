package be.olivierhermans.querydsltest.model;

import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class Form {

    long formId;
    long clientId;
    OffsetDateTime creationTms;
}

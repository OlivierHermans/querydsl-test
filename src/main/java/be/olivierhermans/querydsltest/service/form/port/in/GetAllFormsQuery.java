package be.olivierhermans.querydsltest.service.form.port.in;

import be.olivierhermans.querydsltest.model.Form;

import java.util.Collection;

public interface GetAllFormsQuery {

    Collection<Form> getAllForms();
}

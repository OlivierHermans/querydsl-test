package be.olivierhermans.querydsltest.service.form.port.out;

import be.olivierhermans.querydsltest.model.Form;

import java.util.Collection;

public interface FormFacade {

    Collection<Form> findAll();
}

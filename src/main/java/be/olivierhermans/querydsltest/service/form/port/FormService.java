package be.olivierhermans.querydsltest.service.form.port;

import be.olivierhermans.querydsltest.model.Form;
import be.olivierhermans.querydsltest.service.form.port.in.GetAllFormsQuery;
import be.olivierhermans.querydsltest.service.form.port.out.FormFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
class FormService implements GetAllFormsQuery {

    private final FormFacade formFacade;

    @Override
    public Collection<Form> getAllForms() {
        return formFacade.findAll();
    }
}

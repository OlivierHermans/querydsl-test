package be.olivierhermans.querydsltest.web;

import be.olivierhermans.querydsltest.model.Form;
import be.olivierhermans.querydsltest.service.form.port.in.GetAllFormsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/form")
class FormController {

    private final GetAllFormsQuery getAllFormsQuery;

    @GetMapping
    public Collection<Form> getAllForms() {
        return getAllFormsQuery.getAllForms();
    }
}

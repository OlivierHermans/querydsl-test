package be.olivierhermans.querydsltest.dao.form;

import be.olivierhermans.querydsltest.model.Form;
import be.olivierhermans.querydsltest.service.form.port.out.FormFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
class JpaFormFacade implements FormFacade {

    private final FormRepository repository;
    private final FormEntityMapper mapper;

    @Override
    public Collection<Form> findAll() {
        return repository.findAll().stream().map(mapper::entityToModel).collect(toList());
    }
}

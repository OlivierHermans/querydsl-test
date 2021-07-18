package be.olivierhermans.querydsltest.dao.form;

import be.olivierhermans.querydsltest.model.Form;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface FormEntityMapper {

    Form entityToModel(FormEntity entity);
    FormEntity modelToEntity(Form form);
}

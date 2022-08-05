package com.codeWithAshith.SpringBatch.data;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EmployeeMapper implements FieldSetMapper<Employee> {
//    id,firstName,lastName
    @Override
    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
        return new Employee(fieldSet.readString("id"),
                fieldSet.readString("firstName"),
                fieldSet.readString("lastName")
                );
    }
}

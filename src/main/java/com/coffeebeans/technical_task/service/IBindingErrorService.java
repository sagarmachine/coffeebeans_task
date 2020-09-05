package com.coffeebeans.technical_task.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IBindingErrorService {

    ResponseEntity<?> getErrorResponse(BindingResult bindingResult);


}
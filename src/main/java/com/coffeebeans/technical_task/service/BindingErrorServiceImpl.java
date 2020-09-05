package com.coffeebeans.technical_task.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
public class BindingErrorServiceImpl implements IBindingErrorService {


    @Override
    public ResponseEntity<?> getErrorResponse(BindingResult bindingResult) {
        List<ObjectError>errorList= bindingResult.getAllErrors();
           return new ResponseEntity<>(errorList.get(0).getObjectName()+" "+errorList.get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);

    }


}

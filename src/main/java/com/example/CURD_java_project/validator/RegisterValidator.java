package com.example.CURD_java_project.validator;

import com.example.CURD_java_project.dto.RegisterDTO;
import com.example.CURD_java_project.service.Impl.IUserService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {
    private IUserService userService;

    public RegisterValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO userRegisterDTO, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        if(!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Passwords must match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        if(userService.CheckExistEmail(userRegisterDTO.getEmail())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("email already exists")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }
        return valid;
    }
}

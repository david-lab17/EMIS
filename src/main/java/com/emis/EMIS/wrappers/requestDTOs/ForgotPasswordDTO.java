package com.emis.EMIS.wrappers.requestDTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ForgotPasswordDTO {
    private String email;
    private String newPassword;
}

package com.emis.EMIS.wrappers.requestDTOs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Date;


@Data
public class UserDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    @Email
    @Column(unique = true)
    private String email;
    private String nationalId;
    private String phoneNo;
    private String password;
    private String agencyName;
    private String firmName;
    private String emergencyContact;
    private String businessContact;
    private String businessEmail;
    private String businessPhone;
    private Date agreementStartDate;
    private Date agreementEndDate;
    private String contractDetails;
    private String adminRole;
    private String department;
    private String officePhone;
    private String tscNo;
    private String employmentNo;
    private String officePhoneNo;
    private String registrationNo;
    private String gender;
    private String nationality;
    private String dateOfBirth;
    private int profileId;
    private String relationship;
    private String occupation;




}

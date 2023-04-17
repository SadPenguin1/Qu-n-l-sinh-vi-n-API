package jmaster.io.qlsvservice.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Integer id;
    private String studentCode;
    private UserDTO user;

}
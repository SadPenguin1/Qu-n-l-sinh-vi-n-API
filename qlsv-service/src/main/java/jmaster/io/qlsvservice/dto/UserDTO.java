package jmaster.io.qlsvservice.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Integer id;

    private String username;

    private String password;

    private boolean enabled;

    private String email;

    private List<RoleDTO> roles ;
}

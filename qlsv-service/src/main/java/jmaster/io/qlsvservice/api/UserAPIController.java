package jmaster.io.qlsvservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;
import jmaster.io.qlsvservice.dto.UserDTO;
import jmaster.io.qlsvservice.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserAPIController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<UserDTO> create(@RequestBody @Valid UserDTO userDTO) throws IOException {
        userService.create(userDTO);
        return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userDTO).build();
    }

    @PutMapping("/")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> update(@RequestBody @Valid UserDTO userDTO) throws IOException {
        userService.update(userDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<UserDTO> get(@PathVariable(value = "id") int id) {
        return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(userService.get(id)).build();
    }

    @DeleteMapping("/{id}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        userService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/delete/all/{ids}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
        userService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }


    @PostMapping("/search")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<List<UserDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return userService.find(searchDTO);
    }
}

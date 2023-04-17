package jmaster.io.qlsvservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jmaster.io.qlsvservice.dto.StudentDTO;
import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;

import jmaster.io.qlsvservice.service.StudentService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentAPIController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<StudentDTO> create(@RequestBody @Valid StudentDTO studentDTO) throws IOException {
        studentService.create(studentDTO);
        return ResponseDTO.<StudentDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(studentDTO).build();
    }

    @PutMapping(value = "/")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> update(@RequestBody @Valid StudentDTO studentDTO) throws IOException {
        studentService.update(studentDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping(value = "/{id}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<StudentDTO> get(@PathVariable(value = "id") int id) {
        return ResponseDTO.<StudentDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(studentService.get(id)).build();
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        studentService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/{ids}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
        studentService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }


    @PostMapping("/search")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<List<StudentDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return studentService.find(searchDTO);
    }

}

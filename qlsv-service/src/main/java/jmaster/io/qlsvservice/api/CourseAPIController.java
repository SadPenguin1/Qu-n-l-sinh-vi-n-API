package jmaster.io.qlsvservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jmaster.io.qlsvservice.dto.CourseDTO;
import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;
import jmaster.io.qlsvservice.service.CourseService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseAPIController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<CourseDTO> create(@RequestBody @Valid CourseDTO courseDTO) throws IOException {
        courseService.create(courseDTO);
        return ResponseDTO.<CourseDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(courseDTO).build();
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> update(@RequestBody @Valid CourseDTO courseDTO) throws IOException {
        courseService.update(courseDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<CourseDTO> get(@PathVariable(value = "id") int id) {
        return ResponseDTO.<CourseDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(courseService.get(id)).build();
    }

    @DeleteMapping(value = "/{id}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        courseService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/delete/all/{ids}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
        courseService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }


    @PostMapping("/search")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<List<CourseDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return courseService.find(searchDTO);
    }

}

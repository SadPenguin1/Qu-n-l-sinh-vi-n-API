package jmaster.io.qlsvservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jmaster.io.qlsvservice.dto.ScoreDTO;
import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;
import jmaster.io.qlsvservice.service.ScoreService;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreAPIController {
    @Autowired
    ScoreService scoreService;

    @PostMapping("/")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<ScoreDTO> create(@RequestBody @Valid ScoreDTO scoreDTO) throws IOException {
        // goi qua Service
        scoreService.create(scoreDTO);
        return ResponseDTO.<ScoreDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(scoreDTO).build();
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> update(@ModelAttribute @Valid ScoreDTO scoreDTO) throws IOException {
        scoreService.update(scoreDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<ScoreDTO> get(@PathVariable(value = "id") int id) {
        return ResponseDTO.<ScoreDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(scoreService.get(id)).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        scoreService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
        scoreService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }


    @PostMapping("/search")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<List<ScoreDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return scoreService.find(searchDTO);
    }
}

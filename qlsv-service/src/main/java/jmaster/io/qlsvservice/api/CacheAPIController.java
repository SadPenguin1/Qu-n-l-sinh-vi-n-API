package jmaster.io.qlsvservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jmaster.io.qlsvservice.dto.CacheDTO;
import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.service.CacheService;

import java.util.Set;

@RestController
@RequestMapping("/cache")
public class CacheAPIController {

    @Autowired
    CacheService cacheService;

    @GetMapping("/")
    public ResponseDTO<Set<String>> getCacheNames() {
        return cacheService.getCacheNames();
    }

    @GetMapping("/{name}/keys")
    public ResponseDTO<Set<String>> getKeysByCacheName(@PathVariable(value = "name") String name) {
        return cacheService.getKeysByCacheName(name);
    }

    @DeleteMapping("/")
    public ResponseDTO<Void> clearCache(@RequestBody CacheDTO cacheDTO) {
        cacheService.clearCache(cacheDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }
}
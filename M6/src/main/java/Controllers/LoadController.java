package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Domain.Entities.Load;
import Domain.Services.Impl.LoadService;



@RestController
@RequestMapping("/api/loads")
public class LoadController {

    @Autowired
    private LoadService loadService;

    @PostMapping
    public ResponseEntity<Load> create(@RequestBody Load load) {
        try {
            Load newLoad = loadService.create(load);
            return new ResponseEntity<>(newLoad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<List<Load>> getAll() {
        try {
            List<Load> loads = loadService.readAll();
            return new ResponseEntity<>(loads, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Load> getById(@PathVariable("id") Long loadId) {
        try {
            Load loads = loadService.getById(loadId);
            return new ResponseEntity<>(loads, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Load> update(@PathVariable("id") Long loadId,
                                                             @RequestBody Load updatedLoad) {
        try {
            Load updated = loadService.update(loadId, updatedLoad);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long loadId) {
        try {
            boolean deleted = loadService.getDeleteById(loadId);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

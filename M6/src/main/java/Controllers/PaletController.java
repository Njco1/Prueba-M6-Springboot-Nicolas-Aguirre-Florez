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

import Domain.Entities.Palet;
import Domain.Services.Impl.PaletService;


@RestController
@RequestMapping("/api/pallets")
public class PaletController {
    
    @Autowired
    private PaletService paletService;

    @PostMapping
    public ResponseEntity<Palet> create(@RequestBody Palet palet) {
        try {
            Palet newPalet = paletService.create(palet);
            return new ResponseEntity<>(newPalet, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<List<Palet>> getAll() {
        try {
            List<Palet> palets = paletService.readAll();
            return new ResponseEntity<>(palets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Palet> getById(@PathVariable("id") Long paletId) {
        try {
            Palet palets = paletService.getById(paletId);
            return new ResponseEntity<>(palets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Palet> update(@PathVariable("id") Long paletId,
                                                             @RequestBody Palet updatetPalet) {
        try {
            Palet updated = paletService.update(paletId, updatetPalet);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long paletId) {
        try {
            boolean deleted = paletService.getDeleteById(paletId);
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

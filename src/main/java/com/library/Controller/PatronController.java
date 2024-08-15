package com.library.Controller;

import com.library.Service.imp.PatronServiceimp;
import com.library.DTO.PatronDto;
import com.library.Entity.Patron;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    PatronServiceimp patronServiceimp;

    @PostMapping("/add")
    public ResponseEntity<Patron> addPatron(@RequestBody @Valid PatronDto patronDto){

        return new ResponseEntity<>(patronServiceimp.savepatron(patronDto), HttpStatus.CREATED);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<PatronDto> findPatronById(@PathVariable Long id){

        PatronDto patronDto = patronServiceimp.getPatronByid(id);
        return ResponseEntity.ok(patronDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PatronDto>> getallPatrons(){

        List<PatronDto> patron = patronServiceimp.getallpatrons();
        return ResponseEntity.ok(patron);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatronDto> updatepatron(@RequestBody  PatronDto patronDto, @PathVariable Long id){

        PatronDto patron= patronServiceimp.editePatron(patronDto, id);
        return  ResponseEntity.ok(patron);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletepatronByid(@PathVariable Long id){
        patronServiceimp.deletePatronById(id);
        return ResponseEntity.ok("patron is deleted successfully!");
    }


}

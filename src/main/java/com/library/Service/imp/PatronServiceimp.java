package com.library.Service.imp;


import com.library.Exception.PatronException;
import com.library.Mapper.PatronMapper;
import com.library.Repository.PatronRepo;
import com.library.DTO.PatronDto;
import com.library.Entity.Patron;
import com.library.Service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronServiceimp implements PatronService {

   @Autowired
   private PatronRepo patronRepo;

   @Override
   public Patron savepatron(PatronDto patronDto){

      Patron patron= PatronMapper.INSTANCE.mapToEntity(patronDto);
      return patronRepo.save(patron);
   }

   @Override
   public PatronDto getPatronByid(Long id){

      Patron patron= patronRepo.findById(id).orElseThrow(() -> new PatronException("Patron not found with id: " + id));
      return PatronMapper.INSTANCE.mapToDto(patron);
   }

   @Override
   public List<PatronDto> getallpatrons(){
      List<Patron> patron= patronRepo.findAll();
      return patron.stream().map((PatronMapper.INSTANCE::mapToDto)).collect(Collectors.toList());

   }

   @Override
   public PatronDto editePatron(PatronDto patronDto, Long id) {
      Patron existingPatron = patronRepo.findById(id)
              .orElseThrow(() -> new PatronException("Patron not found with id: " + id));
      Patron updatedPatron = PatronMapper.INSTANCE.mapToEntity(patronDto);
      updatedPatron.setPId(existingPatron.getPId());
      Patron savedPatron = patronRepo.save(updatedPatron);
      return PatronMapper.INSTANCE.mapToDto(savedPatron);
   }


   @Override
   public void deletePatronById(Long id) {
      Patron patron = patronRepo.findById(id)
              .orElseThrow(() -> new PatronException("Patron not found with id: " + id));

      patronRepo.deleteById(id);
   }







}

package com.library.service.imp;


import com.library.exception.PatronException;
import com.library.mapper.PatronMapper;
import com.library.dao.PatronRepo;
import com.library.dto.PatronDto;
import com.library.entity.Patron;
import com.library.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronServiceimp implements PatronService {

   @Autowired
   private PatronRepo patronRepo;

   @Override
   @CacheEvict(value = "patrons", allEntries = true)
   public Patron savepatron(PatronDto patronDto){

      Patron patron= PatronMapper.INSTANCE.mapToEntity(patronDto);
      return patronRepo.save(patron);
   }

   @Override
   @Cacheable(value = "patronsById ", key = "#id")
   public PatronDto getPatronByid(Long id){

      Patron patron= patronRepo.findById(id).orElseThrow(() -> new PatronException("Patron not found with id: " + id));
      return PatronMapper.INSTANCE.mapToDto(patron);
   }

   @Override
   @Cacheable(value = "patrons", key = "#root.methodName")
   public List<PatronDto> getallpatrons(){
      List<Patron> patron= patronRepo.findAll();
      return patron.stream().map((PatronMapper.INSTANCE::mapToDto)).collect(Collectors.toList());

   }

   @Override
   @CacheEvict(value = "patrons", key = "#patronDto.id",allEntries = true)
   public PatronDto editePatron(PatronDto patronDto, Long id) {
      Patron existingPatron = patronRepo.findById(id)
              .orElseThrow(() -> new PatronException("Patron not found with id: " + id));
      Patron updatedPatron = PatronMapper.INSTANCE.mapToEntity(patronDto);
      updatedPatron.setPId(existingPatron.getPId());
      Patron savedPatron = patronRepo.save(updatedPatron);
      return PatronMapper.INSTANCE.mapToDto(savedPatron);
   }


   @Override
   @CacheEvict(value = "deletePatrons", key = "#id",allEntries = true)
   public void deletePatronById(Long id) {
      Patron patron = patronRepo.findById(id)
              .orElseThrow(() -> new PatronException("Patron not found with id: " + id));

      patronRepo.deleteById(id);
   }







}

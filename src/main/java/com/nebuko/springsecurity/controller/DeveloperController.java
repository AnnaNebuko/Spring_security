package com.nebuko.springsecurity.controller;

import com.nebuko.springsecurity.model.Developer;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {

  private final List<Developer> developers = List.of(
      new Developer(1L, "Seva"),
      new Developer(2L, "Ilya"),
      new Developer(3L, "Mickael"),
      new Developer(4L, "Ann")
  );

  @GetMapping
  public List<Developer> getAll() {
    return developers;
  }

  @GetMapping("/{id}")
  public Developer getById(@PathVariable Long id) {
    return developers.stream().filter(developer -> developer.getId().equals(id))
        .findFirst().orElse(null);
  }

}

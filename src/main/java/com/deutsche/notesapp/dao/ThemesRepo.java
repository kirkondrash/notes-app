package com.deutsche.notesapp.dao;

import com.deutsche.notesapp.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "themes")
public interface ThemesRepo extends JpaRepository<Theme, Long> {
    String ALL_THEMES = "ALL";

}

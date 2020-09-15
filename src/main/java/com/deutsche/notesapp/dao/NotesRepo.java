package com.deutsche.notesapp.dao;

import com.deutsche.notesapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "notes")
public interface NotesRepo extends JpaRepository<Note, Long> {

    @RestResource(path = "byTheme")
    List<Note> findByThemeName(@Param("theme") String theme);

}

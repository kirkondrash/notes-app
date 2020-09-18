package com.deutsche.notesapp.service;

import com.deutsche.notesapp.dao.NotesRepo;
import com.deutsche.notesapp.dao.ThemesRepo;
import com.deutsche.notesapp.model.Note;
import com.deutsche.notesapp.model.Theme;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = NoteViewService.class)
class NoteViewServiceTest {

    @Autowired
    NoteViewService noteViewService;

    @MockBean
    NotesRepo notesRepo;
    @MockBean
    ThemesRepo themesRepo;

    @Test
    void whenIdNotFound_ThrowException() {
        Mockito.when(notesRepo.findByThemeName("ALL")).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class,() -> noteViewService.deleteNoteById(42));
    }

    @Test
    void whenIdFound_Ok() {
        Note stub = new Note(1L,"title","theme",new Theme(2L,"common"));
        Mockito.when(notesRepo.findById(1L)).thenReturn(Optional.of(stub));
        Model m = new ExtendedModelMap();
        noteViewService.fillModelWithNoteById(1L,m);
        assertEquals(m.getAttribute("note"),stub);
    }
}
package com.deutsche.notesapp.controller;

import com.deutsche.notesapp.dao.NotesRepo;
import com.deutsche.notesapp.dao.ThemesRepo;
import com.deutsche.notesapp.model.Note;
import com.deutsche.notesapp.model.Theme;
import com.deutsche.notesapp.service.NoteViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.deutsche.notesapp.dao.ThemesRepo.ALL_THEMES;

@Controller
@RequestMapping("/notes")
public class NoteViewController {

    private final NoteViewService noteViewService;

    @Autowired
    public NoteViewController(NoteViewService noteViewService) {
        this.noteViewService = noteViewService;
    }

    @GetMapping
    public String viewAllNotes(@RequestParam(defaultValue = ALL_THEMES) String theme, Model model) {
        noteViewService.fillModelWithNotesByTheme(theme, model);
        return "all-notes";
    }

    @GetMapping("/{id}")
    public String viewNote(@PathVariable("id") Long id, Model model) {
        noteViewService.fillModelWIthNodeById(id, model);
        return "view-note";
    }

    @GetMapping("/add_note")
    public String showAddNoteForm(Note note){
        return "add-note";
    }

    @PostMapping
    public String addNote(@Validated Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-note";
        }
        noteViewService.saveNote(note);
        noteViewService.fillModelWithNotes(model);
        return "all-notes";
    }

    @GetMapping("/edit_note/{id}")
    public String showEditNoteForm(@PathVariable("id") long id, Model model) {
        noteViewService.fillModelWIthNodeById(id, model);
        return "edit-note";
    }

    @PostMapping("/edit_note/{id}")
    public String editNote(@PathVariable("id") long id, @Validated Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            note.setId(id);
            return "edit-note";
        }
        noteViewService.saveNote(note);
        noteViewService.fillModelWithNotes(model);
        return "all-notes";
    }

    @GetMapping("/delete_note/{id}")
    public String deleteNote(@PathVariable("id") long id, Model model) {
        noteViewService.deleteNoteById(id);
        noteViewService.fillModelWithNotes(model);
        return "all-notes";
    }
}

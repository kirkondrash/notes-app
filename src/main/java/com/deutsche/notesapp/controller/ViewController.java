package com.deutsche.notesapp.controller;

import com.deutsche.notesapp.dao.NotesRepo;
import com.deutsche.notesapp.dao.ThemesRepo;
import com.deutsche.notesapp.model.Note;
import com.deutsche.notesapp.model.Theme;
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
public class ViewController {

    private final NotesRepo notesRepo;
    private final ThemesRepo themesRepo;

    @Autowired
    public ViewController(NotesRepo notesRepo, ThemesRepo themesRepo) {
        this.notesRepo = notesRepo;
        this.themesRepo = themesRepo;
    }

    @GetMapping
    public String viewAllNotes(@RequestParam(defaultValue = ALL_THEMES) String theme, Model model) {
        List<Note> notes = theme.equals("ALL") ? notesRepo.findAll() : notesRepo.findByThemeName(theme);
        model.addAttribute("notes", notes);
        model.addAttribute("notes_size", notes.size());
        model.addAttribute("selected_theme", theme);
        model.addAttribute("themes", themesRepo.findAll().stream().map(Theme::getName).collect(Collectors.toSet()));
        return "all-notes";
    }

    @GetMapping("/{id}")
    public String viewNote(@PathVariable("id") Long id, Model model) {
        Note note = notesRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id));
        model.addAttribute("note", note);
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
        notesRepo.save(note);
        return viewAllNotes(ALL_THEMES, model);
    }

    @GetMapping("/edit_note/{id}")
    public String showEditNoteForm(@PathVariable("id") long id, Model model) {
        Note note = notesRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id));
        model.addAttribute("note", note);
        return "edit-note";
    }

    @PostMapping("/edit_note/{id}")
    public String editNote(@PathVariable("id") long id, @Validated Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            note.setId(id);
            return "edit-note";
        }
        notesRepo.save(note);
        return viewAllNotes(ALL_THEMES, model);
    }

    @GetMapping("/delete_note/{id}")
    public String deleteNote(@PathVariable("id") long id, Model model) {
        Note note = notesRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id));
        notesRepo.delete(note);
        return viewAllNotes(ALL_THEMES, model);
    }
}

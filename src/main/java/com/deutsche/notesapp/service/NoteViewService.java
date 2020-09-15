package com.deutsche.notesapp.service;

import com.deutsche.notesapp.dao.NotesRepo;
import com.deutsche.notesapp.dao.ThemesRepo;
import com.deutsche.notesapp.model.Note;
import com.deutsche.notesapp.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteViewService {

    private final NotesRepo notesRepo;
    private final ThemesRepo themesRepo;

    @Autowired
    public NoteViewService(NotesRepo notesRepo, ThemesRepo themesRepo) {
        this.notesRepo = notesRepo;
        this.themesRepo = themesRepo;
    }

    public void fillModelWithNotes(Model model){
        fillModelWithNotesByTheme(ThemesRepo.ALL_THEMES,model);
    }

    public void fillModelWithNotesByTheme(String theme, Model model) {
        List<Note> notes = theme.equals("ALL") ? notesRepo.findAll() : notesRepo.findByThemeName(theme);
        model.addAttribute("notes", notes);
        model.addAttribute("notes_size", notes.size());
        model.addAttribute("selected_theme", theme);
        model.addAttribute("themes", themesRepo.findAll().stream().map(Theme::getName).collect(Collectors.toSet()));
    }

    public void fillModelWIthNodeById(Long id, Model model) {
        Note note = notesRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id));
        model.addAttribute("note", note);
    }

    public void saveNote(Note note) {
        notesRepo.save(note);
    }

    public void deleteNoteById(long id) {
        Note note = notesRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id));
        notesRepo.delete(note);
    }
}
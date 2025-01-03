package com.example.backend.controller;

import com.example.backend.entity.Profile;
import com.example.backend.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;
    @GetMapping("/list")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        return profileOptional.map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = profileRepository.save(profile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        if (profileOptional.isPresent()) {
            profile.setId(id);
            Profile updatedProfile = profileRepository.save(profile);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
package vu.serhiimytsyk.guesssong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vu.serhiimytsyk.guesssong.entity.album.Album;
import vu.serhiimytsyk.guesssong.entity.Artist;
import vu.serhiimytsyk.guesssong.entity.lyrics.Lyric;
import vu.serhiimytsyk.guesssong.service.MusicService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final MusicService musicService;

    @Autowired
    public ApiController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/artists")
    public ResponseEntity<List<Artist>> getArtists() {
        return new ResponseEntity<>(musicService.getArtists(), HttpStatus.OK);
    }

    @PostMapping("/artists")
    public ResponseEntity<?> selectArtist(@RequestBody Artist artist) {
        try {
            musicService.setArtist(artist);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAlbums() {
        List<Album> albums = musicService.getAlbums();

        if (albums == null) {
            System.out.println("Artist is not chosen");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(albums, HttpStatus.OK);
        }
    }

    @PostMapping("/albums")
    public ResponseEntity<?> selectAlbum(@RequestBody Album album) {
        try {
            musicService.setAlbum(album);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lyrics/{k}")
    public ResponseEntity<List<Lyric>> getRandomLyrics(@PathVariable int k) {
        try {
            List<Lyric> lyrics = musicService.getRandomLyrics(k);
            return new ResponseEntity<>(lyrics, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

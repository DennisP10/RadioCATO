package com.musicapp.api.controller;

import com.musicapp.api.model.*;
import com.musicapp.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// --- SEGURIDAD CORS (CRÍTICO PARA LOGIN) ---
@CrossOrigin(originPatterns = "*", allowCredentials = "true") 
@RestController
@RequestMapping("/api/v1")
public class MusicController {

    @Autowired private UserByEmailRepository authRepo;
    @Autowired private ArtistRepository artistRepo;
    @Autowired private SongsByPlaylistRepository playlistSongsRepo;
    @Autowired private SongStatsRepository statsRepo;
    @Autowired private SongsByAlbumRepository albumSongsRepo;
    
    // --- REPOSITORIOS NUEVOS (Q5, Q12, Q7) ---
    @Autowired private UserHistoryRepository historyRepo;
    @Autowired private UserSearchRepository searchRepo;
    @Autowired private UserPlaylistRepository userPlaylistRepo; // <-- NUEVO PARA Q7

    private final UUID MAIN_PLAYLIST_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    // =================================================================================
    //                SINCRONIZACIÓN CON CARPETA FÍSICA
    // =================================================================================
    @PostMapping("/music/sync")
    public ResponseEntity<String> syncMusicFolder() {
        Path musicPath = Paths.get("/music");
        
        if (!Files.exists(musicPath) || !Files.isDirectory(musicPath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: La carpeta física /music no existe en el servidor.");
        }

        try (Stream<Path> paths = Files.walk(musicPath)) {
            List<Path> mp3Files = paths
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().toLowerCase().endsWith(".mp3"))
                .collect(Collectors.toList());

            if (mp3Files.isEmpty()) {
                return ResponseEntity.ok("Sincronización terminada: No se encontraron archivos .mp3");
            }

            for (Path file : mp3Files) {
                try {
                    String fileName = file.getFileName().toString();
                    String songTitle = fileName.replace(".mp3", "");
                    UUID songId = UUID.nameUUIDFromBytes(fileName.getBytes());

                    SongsByPlaylist song = new SongsByPlaylist();
                    
                    // Corrección de constructor (Setters)
                    SongsByPlaylistKey key = new SongsByPlaylistKey();
                    key.setPlaylistId(MAIN_PLAYLIST_ID);
                    key.setAddedAt(Instant.now());
                    key.setSongId(songId);
                    
                    song.setKey(key);
                    song.setTitle(songTitle);
                    song.setArtistName(fileName); 
                    song.setDuration(0);

                    playlistSongsRepo.save(song);
                } catch (Exception e) {
                    System.err.println("Error procesando " + file.getFileName() + ": " + e.getMessage());
                }
            }
            return ResponseEntity.ok("Sincronización exitosa: " + mp3Files.size() + " canciones acopladas.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error de sistema de archivos: " + e.getMessage());
        }
    }

    // =================================================================================
    //                               AUTENTICACIÓN (Q1)
    // =================================================================================
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            return authRepo.findById(email)
                .map(authData -> {
                    if (authData.getPassword().equals(password)) {
                        authData.setPassword(null); 
                        return ResponseEntity.ok((Object)authData);
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servicio");
        }
    }

    // =================================================================================
    //                        POPULARES (Contador Global)
    // =================================================================================
    @GetMapping("/home/popular")
    public ResponseEntity<List<SongsByPlaylist>> getPopularSongs() {
        try {
            List<SongStats> allStats = statsRepo.findAll();
            if (allStats == null || allStats.isEmpty()) return ResponseEntity.ok(Collections.emptyList());

            Map<UUID, Long> statsMap = allStats.stream()
                .collect(Collectors.toMap(SongStats::getSongId, SongStats::getTotalPlays));

            List<UUID> topSongIds = allStats.stream()
                .sorted((s1, s2) -> Long.compare(s2.getTotalPlays(), s1.getTotalPlays()))
                .limit(10)
                .map(SongStats::getSongId)
                .collect(Collectors.toList());

            List<SongsByPlaylist> popularSongs = playlistSongsRepo.findAll()
                .stream()
                .filter(song -> topSongIds.contains(song.getKey().getSongId()))
                .map(song -> {
                    song.setPlayCount(statsMap.getOrDefault(song.getKey().getSongId(), 0L));
                    return song;
                })
                .filter(distinctByKey(s -> s.getKey().getSongId())) 
                .sorted((s1, s2) -> Long.compare(s2.getPlayCount(), s1.getPlayCount()))
                .collect(Collectors.toList());

            return ResponseEntity.ok(popularSongs);
        } catch (Exception e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    // =================================================================================
    //                             BIBLIOTECA (Q4, Q8)
    // =================================================================================
    @GetMapping("/playlists/{playlistId}/songs")
    public ResponseEntity<List<SongsByPlaylist>> getPlaylistSongs(@PathVariable UUID playlistId) {
        try {
            List<SongsByPlaylist> songs = playlistSongsRepo.findByKeyPlaylistId(playlistId);
            return ResponseEntity.ok(songs != null ? songs : Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    // =================================================================================
    //                NUEVO: PLAYLISTS DEL USUARIO (Q7)
    // =================================================================================
    @GetMapping("/users/{userId}/playlists")
    public ResponseEntity<List<UserPlaylist>> getUserPlaylists(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(userPlaylistRepo.findByKeyUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    // =================================================================================
    //                             ARTISTAS (Q3, Q10)
    // =================================================================================
    @GetMapping("/artists/{name}/songs")
    public ResponseEntity<List<Artist>> getArtistSongs(@PathVariable String name) {
        try {
            List<Artist> songs = artistRepo.findByName(name);
            return ResponseEntity.ok(songs);
        } catch (Exception e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    // =================================================================================
    //                               ÁLBUMES (Q2, Q11)
    // =================================================================================
    @GetMapping("/albums/{albumId}/songs")
    public ResponseEntity<List<SongsByAlbum>> getAlbumSongs(@PathVariable UUID albumId) {
        try {
            List<SongsByAlbum> songs = albumSongsRepo.findByKeyAlbumId(albumId);
            return ResponseEntity.ok(songs);
        } catch (Exception e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    // =================================================================================
    //                                ESTADÍSTICAS GLOBAL
    // =================================================================================
    @PostMapping("/songs/{songId}/play")
    public ResponseEntity<String> registerPlay(@PathVariable UUID songId) {
        try {
            if (songId == null) return ResponseEntity.badRequest().build();
            statsRepo.incrementPlays(songId);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Stats offline");
        }
    }

    // =================================================================================
    //                NUEVO: HISTORIAL DE USUARIO (Q5)
    // =================================================================================
    @GetMapping("/users/{userId}/history")
    public ResponseEntity<List<UserHistory>> getUserHistory(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(historyRepo.findByKeyUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @PostMapping("/users/{userId}/history")
    public ResponseEntity<?> addToHistory(@PathVariable UUID userId, @RequestBody Map<String, String> body) {
        try {
            UserHistory h = new UserHistory();
            h.setKey(new UserHistoryKey(userId, Instant.now()));
            h.setSongId(UUID.fromString(body.get("songId")));
            h.setDeviceType("WEB");
            historyRepo.save(h);
            return ResponseEntity.ok("Saved");
        } catch (Exception e) { 
            return ResponseEntity.badRequest().body("Error saving history: " + e.getMessage()); 
        }
    }

    // =================================================================================
    //                NUEVO: BÚSQUEDA (Q12)
    // =================================================================================
    @PostMapping("/users/{userId}/search")
    public ResponseEntity<?> logSearch(@PathVariable UUID userId, @RequestBody Map<String, String> body) {
        try {
            UserSearch s = new UserSearch();
            s.setKey(new UserSearchKey(userId, Instant.now()));
            s.setQueryText(body.get("query"));
            s.setResultsCount(0); 
            searchRepo.save(s);
            return ResponseEntity.ok("Logged");
        } catch (Exception e) { 
            return ResponseEntity.badRequest().build(); 
        }
    }

    // Auxiliar
    public static <T> java.util.function.Predicate<T> distinctByKey(java.util.function.Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
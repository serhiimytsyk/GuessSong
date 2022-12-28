package vu.serhiimytsyk.guesssong.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vu.serhiimytsyk.guesssong.entity.*;
import vu.serhiimytsyk.guesssong.entity.album.Album;
import vu.serhiimytsyk.guesssong.entity.album.AlbumBox;
import vu.serhiimytsyk.guesssong.entity.lyrics.Lyric;
import vu.serhiimytsyk.guesssong.entity.lyrics.Lyrics;
import vu.serhiimytsyk.guesssong.entity.track.Track;
import vu.serhiimytsyk.guesssong.entity.track.TrackBox;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {
    @Value("${musixmatch.apiKey}")
    private String apiKey;

    @Value("${musixmatch.url}")
    private String URL;

    private final List<Artist> artists;

    private List<Album> albums;

    private List<Lyric> lyrics;

    public MusicService() {
        Artist artist1 = new Artist(259675, "Taylor Swift");
        Artist artist2 = new Artist(13895270, "Imagine Dragons");
        Artist artist3 = new Artist(926, "Skillet");
        Artist artist4 = new Artist(164200, "Bring Me The Horizon");
        artists = new ArrayList<>();
        artists.add(artist1);
        artists.add(artist2);
        artists.add(artist3);
        artists.add(artist4);
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtist(Artist artist) throws IOException, InterruptedException {
        updateAlbums(artist);
        lyrics = null;
    }

    private void updateAlbums(Artist artist) throws IOException, InterruptedException {
        final String ALBUMS_URL_FORMAT = URL + "artist.albums.get?%s=%s&%s=%s";

        try {
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create(String.format(ALBUMS_URL_FORMAT, "apikey", apiKey, "artist_id", artist.getId())))
                    .header("accept", "application/json")
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().
                    send(request, HttpResponse.BodyHandlers.ofString());
            String fullResponse = response.body();
            String listResponse = fullResponse.
                    substring(fullResponse.indexOf("\"album_list\":") + 13, fullResponse.lastIndexOf('}') - 2);
            ObjectMapper mapper = new ObjectMapper();
            List<AlbumBox> boxes = mapper.readValue(listResponse, new TypeReference<>(){});
            albums = boxes.stream().map(AlbumBox::getAlbum).toList();
        } catch (Exception e) {
            albums = null;
            throw e;
        }
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbum(Album album) throws IOException, InterruptedException {
        try {
            List<Track> tracks = getTracks(album);
            lyrics = new ArrayList<>();
            for (Track track : tracks) {
                addLyrics(track);
            }
        } catch (Exception e) {
            lyrics = null;
            throw e;
        }
    }
    private List<Track> getTracks(Album album) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        final String TRACKS_URL_FORMAT = URL + "album.tracks.get?%s=%s&%s=%s";

        HttpRequest tracksRequest = HttpRequest.newBuilder(
                        URI.create(String.format(TRACKS_URL_FORMAT, "apikey", apiKey, "album_id", album.getId())))
                .header("accept", "application/json")
                .build();
        HttpResponse<String> tracksResponse = HttpClient.newHttpClient().
                send(tracksRequest, HttpResponse.BodyHandlers.ofString());
        String fullResponse = tracksResponse.body();
        String listResponse = fullResponse.
                substring(fullResponse.indexOf("\"track_list\":") + 13, fullResponse.lastIndexOf('}') - 2);
        List<TrackBox> boxList = mapper.readValue(listResponse, new TypeReference<>(){});
        return boxList.stream().map(TrackBox::getTrack).toList();
    }

    private void addLyrics(Track track) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        final String LYRICS_URL_FORMAT = URL + "track.lyrics.get?%s=%s&%s=%s";

        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(String.format(LYRICS_URL_FORMAT, "apikey", apiKey, "track_id", track.getId())))
                .header("accept", "application/json")
                .build();
        HttpResponse<String> lyricsResponse = HttpClient.newHttpClient().
                send(request, HttpResponse.BodyHandlers.ofString());
        String fullResponse = lyricsResponse.body();
        String response = fullResponse.
                substring(fullResponse.indexOf("\"lyrics\":") + 9, fullResponse.lastIndexOf('}') - 2);
        Lyrics lyricsText = mapper.readValue(response, new TypeReference<>(){});
        String[] lines = lyricsText.getBody().split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (checkLine(lines[i]))
                lyrics.add(new Lyric(track.getId(), track.getName(), i + 1, lines[i]));
        }
    }

    private boolean checkLine(String s) {
        return s != null &&
                !s.isEmpty() &&
                !s.equals("******* This Lyrics is NOT for Commercial use *******") &&
                !s.equals("...") &&
                !(s.startsWith("(") && s.endsWith(")"));
    }

    public List<Lyric> getRandomLyrics(int k) {
        List<Lyric> randomLyrics = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int index = (int) (Math.random() * lyrics.size());
            randomLyrics.add(lyrics.get(index));
        }
        return randomLyrics;
    }
}

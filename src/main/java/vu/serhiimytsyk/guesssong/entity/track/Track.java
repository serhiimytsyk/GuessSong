package vu.serhiimytsyk.guesssong.entity.track;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties({"track_name_translation_list", "track_rating", "commontrack_id", "instrumental", "explicit",
        "has_lyrics", "has_subtitles", "has_richsync", "num_favourite", "album_id", "album_name", "artist_id",
        "artist_name", "track_share_url", "track_edit_url", "restricted", "updated_time", "primary_genres"})
public class Track {
    @JsonProperty("track_id")
    private Integer id;

    @JsonProperty("track_name")
    private String name;
}

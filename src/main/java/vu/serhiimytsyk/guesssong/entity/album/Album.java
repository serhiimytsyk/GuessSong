package vu.serhiimytsyk.guesssong.entity.album;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties({"album_mbid", "album_rating", "album_release_date", "artist_name", "artist_id", "primary_genres",
        "album_pline", "album_label", "restricted", "updated_time", "external_ids", "album_copyright"})
public class Album {
    @JsonProperty("album_id")
    private Integer id;

    @JsonProperty("album_name")
    private String name;
}

package vu.serhiimytsyk.guesssong.entity.lyrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties({"lyrics_id", "explicit", "script_tracking_url", "pixel_tracking_url",
        "lyrics_copyright", "updated_time"})
public class Lyrics {
    @JsonProperty("lyrics_body")
    private String body;
}

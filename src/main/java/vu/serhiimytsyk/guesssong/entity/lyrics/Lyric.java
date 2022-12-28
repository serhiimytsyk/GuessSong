package vu.serhiimytsyk.guesssong.entity.lyrics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Lyric {
    @JsonProperty("track_id")
    private Integer trackId;

    @JsonProperty("track_name")
    private String trackName;

    @JsonProperty("line_number")
    private Integer line;

    @JsonProperty("line_text")
    private String text;
}

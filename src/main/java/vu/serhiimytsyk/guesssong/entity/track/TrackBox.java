package vu.serhiimytsyk.guesssong.entity.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrackBox {
    @JsonProperty("track")
    private Track track;
}

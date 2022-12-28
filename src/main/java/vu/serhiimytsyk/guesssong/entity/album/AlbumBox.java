package vu.serhiimytsyk.guesssong.entity.album;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AlbumBox {
    @JsonProperty("album")
    private Album album;
}

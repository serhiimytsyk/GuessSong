package vu.serhiimytsyk.guesssong.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Artist {
    @JsonProperty("artist_id")
    private Integer id;

    @JsonProperty("artist_name")
    private String name;
}

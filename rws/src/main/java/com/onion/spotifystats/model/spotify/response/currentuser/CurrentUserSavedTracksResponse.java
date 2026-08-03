package com.onion.spotifystats.model.spotify.response.currentuser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CurrentUserSavedTracksResponse {
    int total;
    List<SavedTracksItem> items;
}

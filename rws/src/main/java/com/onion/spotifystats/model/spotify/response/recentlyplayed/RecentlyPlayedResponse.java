package com.onion.spotifystats.model.spotify.response.recentlyplayed;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecentlyPlayedResponse {
    private List<RecentlyPlayedItem> items;
}

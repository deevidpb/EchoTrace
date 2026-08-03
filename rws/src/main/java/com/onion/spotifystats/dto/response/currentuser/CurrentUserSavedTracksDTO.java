package com.onion.spotifystats.dto.response.currentuser;

import java.util.List;

public record CurrentUserSavedTracksDTO(
        int total,
        List<SavedTracksItemDTO> tracks
) {
}

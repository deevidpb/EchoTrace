package com.onion.spotifystats.dto.response.playlist;

import com.onion.spotifystats.dto.response.utils.ImageDTO;

import java.util.List;

public record   PlayListDTO(
        String id,
        String name,
        String description,
        boolean collaborative,
        boolean isPublic,
        String spotifyUrl,
        List<ImageDTO> images,
        int nItems,
        UserDTO user
) {
}

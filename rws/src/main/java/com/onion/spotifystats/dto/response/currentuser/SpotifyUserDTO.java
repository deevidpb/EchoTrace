package com.onion.spotifystats.dto.response.currentuser;

import com.onion.spotifystats.dto.response.utils.ImageDTO;

import java.util.List;

public record SpotifyUserDTO(
        String id,
        String name,
        String email,
        int followers,
        String url,
        List<ImageDTO> images
) {}

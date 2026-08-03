package com.onion.spotifystats.dto.response.currentuser;

import com.onion.spotifystats.dto.response.playlist.PlayListDTO;

import java.util.List;

public record CurrentUserPlaylistsDTO (
        int total,
        List<PlayListDTO> items
){}

package com.onion.spotifystats.dto.response.artist;



import com.onion.spotifystats.dto.response.utils.ImageDTO;

import java.util.List;

public record ArtistComplexDTO(
        String id,
        String name,
        String url,
        List<ImageDTO> images,
        int rankChange,
        boolean isNew
) {}

package com.onion.spotifystats.service.mapper;

import com.onion.spotifystats.dto.response.utils.ImageDTO;
import com.onion.spotifystats.model.spotify.response.utils.Image;

public class ImageMapper {
    private ImageMapper() {}

    public static ImageDTO map (Image image) {
        return new ImageDTO(image.getUrl(),image.getHeight(),image.getWidth());
    }
}

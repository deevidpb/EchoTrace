// Tipos compartidos entre el frontend y las respuestas del backend.

export type TimeRange = "short_term" | "medium_term" | "long_term"

export interface Image {
    url: string
    height: number | null
    width: number | null
}

export interface SpotifyUser {
    id: string
    name: string
    email?: string
    images: Image[]
    followers: number
    url: string
    plan: string
}

export interface Artist {
    id: string
    name: string
    url: string
    images?: Image[] | null
    rankChange?: number
    isNew?: boolean
}


export interface Album {
    id: string
    name: string
    totalTracks: number
    images: Image[] | null
    url: string
    artists: Artist[]
}
export interface Track {
    id: string
    name: string
    artists: Artist[]
    album: Album
    durationMs: number
    url?: string | null
    played_at?: string
    rankChange?: number
    isNew?: boolean
}

export interface RecentlyItem {
    track: Track
    playedAt: string
}

export interface PlaybackState {
    isPlaying: boolean
    track: Track | null
    progressMs: number
    durationMs: number
    repeatMode: "off" | "track" | "context"
    shuffleState: boolean
}

// ───────────────────────── Social ─────────────────────────
// Spotify ya no expone un grafo social público en su Web API, así que estos
// datos provendrían de tu propio backend (tabla de follows + estadísticas
// agregadas por usuario). La forma se mantiene desacoplada de la UI.

export interface SocialUser {
    id: string
    display_name: string
    handle: string // @usuario
    images: Image[]
    isFollowing: boolean // ¿yo le sigo?
    followsYou: boolean // ¿me sigue?
    topGenre: string
    online: boolean
}

// Tipos de eventos del feed de actividad de personas que sigues.
export type ActivityType = "now_playing" | "liked" | "playlist" | "discovery" | "recommended_to_you"

export interface FriendActivity {
    id: string
    user: SocialUser
    type: ActivityType
    track?: Track
    playlistName?: string
    note?: string // mensaje al recomendar
    timeAgo: string
}

// Puntuación de compatibilidad musical con otra persona.
export interface Compatibility {
    user: SocialUser
    score: number // 0-100
    sharedArtists: { id: string; name: string; images: Image[] }[]
    sharedGenres: string[]
    sharedTracks: number
}

// Recomendación que envío / recibo de otra persona.
export interface Recommendation {
    id: string
    from: SocialUser
    track: Track
    note: string
    timeAgo: string
    liked: boolean
}

// Forma agregada que consume la UI social.
export interface SocialSnapshot {
    feed: FriendActivity[]
    compatibility: Compatibility[]
    following: SocialUser[]
    suggestions: SocialUser[] // personas que podrías seguir
    inbox: Recommendation[] // recomendaciones que me han enviado
}

// Forma agregada que consume la UI del dashboard.
export interface StatsSnapshot {
    user: SpotifyUser
    topTracks: Track[]
    topArtists: Artist[]
    recentlyPlayed: RecentlyItem[]
    topAlbums: Album[]
    breakthroughArtist: Artist | null
    totals: {
        nPlaylists: number
        savedTracks: number
        followed: number
    }
}

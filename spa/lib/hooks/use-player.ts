import useSWR, { useSWRConfig } from "swr"
import { getPlaybackState, playTrack, resumePlayback, pausePlayback, nextTrack, previousTrack} from "@/lib/api/player"
import type { PlaybackState } from "@/lib/spotify/types"

export function usePlayer() {
  const { cache } = useSWRConfig()
  
  const { data: playback, isLoading, mutate } = useSWR<PlaybackState>("player", getPlaybackState, {
    refreshInterval: 25000, // Actualizar cada 25 segundos
    revalidateOnFocus: false, // Desactivar refresh al cambiar de pestaña
    revalidateOnReconnect: false, // Desactivar refresh al reconectar
    dedupingInterval: 24000,
    shouldRetryOnError: false,
  })

  const refreshState = async () => {
    try {
      // Limpiar cache primero
      // cache.delete("player")
      // Forzar revalidación inmediata
      // await mutate(undefined, true)
      await mutate()
    } catch (error) {
      console.error("Error refreshing player state:", error)
    }
  }

  return {
    playback,
    isLoading,
    refresh: refreshState,
    play: async (trackUri: string) => {
      await playTrack(trackUri)
      // await refreshState()
    },
    resume: async () => {
      await resumePlayback()
      // await refreshState()
    },
    pause: async () => {
      await pausePlayback()
      // await refreshState()
    },
    next: async () => {
      await nextTrack()
      await refreshState()
    },
    previous: async () => {
      await previousTrack()
      await refreshState()
    },
  }
}

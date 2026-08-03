import { apiFetch } from "./client"
import type { PlaybackState } from "@/lib/spotify/types"

export async function getPlaybackState(): Promise<PlaybackState> {
  return apiFetch<PlaybackState>("/api/web/player")
}

export async function playTrack(trackUri: string): Promise<void> {
  await apiFetch<void>("/api/web/player/play", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ uri: trackUri }),
  })
}

export async function resumePlayback(): Promise<void> {
  await apiFetch<void>("/api/web/player/play", { method: "PUT" })
}

export async function pausePlayback(): Promise<void> {
  await apiFetch<void>("/api/web/player/pause", { method: "PUT" })
}

export async function nextTrack(): Promise<void> {
  await apiFetch<void>("/api/web/player/next", { method: "POST" })
}

export async function previousTrack(): Promise<void> {
  await apiFetch<void>("/api/web/player/previous", { method: "POST" })
}

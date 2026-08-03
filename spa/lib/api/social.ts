import { apiFetch } from "./client"
import type { SocialSnapshot } from "@/lib/spotify/types"

export async function fetchSocial(): Promise<SocialSnapshot> {
  return apiFetch<SocialSnapshot>("/api/social")
}

export interface SendRecommendationPayload {
  toUserId: string
  trackId: string
  note?: string
}

export async function sendRecommendation(payload: SendRecommendationPayload): Promise<void> {
  await apiFetch<void>("/api/social/recommendations", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  })
}

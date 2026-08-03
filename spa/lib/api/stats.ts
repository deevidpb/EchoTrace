import { apiFetch } from "./client"
import type { StatsSnapshot, TimeRange } from "@/lib/spotify/types"

export async function fetchStats(timeRange: TimeRange = "medium_term"): Promise<StatsSnapshot> {
  return apiFetch<StatsSnapshot>(`/api/web/stats?time_range=${timeRange}`)
}

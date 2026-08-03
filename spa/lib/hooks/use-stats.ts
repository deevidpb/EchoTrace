"use client"

import useSWR from "swr"
import { fetchStats } from "@/lib/api/stats"
import type { TimeRange } from "@/lib/spotify/types"

export function useStats(timeRange: TimeRange = "medium_term") {
  const { data, error, isLoading, mutate } = useSWR(
    ["stats", timeRange],
    () => fetchStats(timeRange),
    { revalidateOnFocus: false },
  )

  return { stats: data, error, isLoading, refresh: mutate }
}

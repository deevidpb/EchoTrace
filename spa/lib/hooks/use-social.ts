"use client"

import useSWR from "swr"
import { fetchSocial } from "@/lib/api/social"

export function useSocial() {
  const { data, error, isLoading, mutate } = useSWR(
    "social",
    fetchSocial,
    { revalidateOnFocus: false },
  )

  return { social: data, error, isLoading, refresh: mutate }
}

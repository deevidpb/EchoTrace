"use client"

import { useCallback, useEffect, useMemo, useState } from "react"
import { motion } from "motion/react"
import { Users } from "lucide-react"
import { AuroraBackground } from "@/components/aurora-background"
import { Topbar } from "@/components/dashboard/topbar"
import { ActivityFeed } from "@/components/social/activity-feed"
import { CompatibilityPanel } from "@/components/social/compatibility-panel"
import { FollowingPanel } from "@/components/social/following-panel"
import { RecommendTrack } from "@/components/social/recommend-track"
import { RecommendationsInbox } from "@/components/social/recommendations-inbox"
import { useSocial } from "@/lib/hooks/use-social"
import { useStats } from "@/lib/hooks/use-stats"
import type { Recommendation, SocialUser } from "@/lib/spotify/types"

const easeOut = [0.22, 1, 0.36, 1] as const

export function Social() {
  const { stats } = useStats()
  const { social, isLoading } = useSocial()

  const [following, setFollowing] = useState<SocialUser[]>([])
  const [suggestions, setSuggestions] = useState<SocialUser[]>([])
  const [inbox, setInbox] = useState<Recommendation[]>([])
  const [feed, setFeed] = useState(social?.feed ?? [])

  useEffect(() => {
    if (!social) return
    setFollowing(social.following)
    setSuggestions(social.suggestions)
    setInbox(social.inbox)
    setFeed(social.feed)
  }, [social])

  const followingIds = useMemo(() => new Set(following.map((u) => u.id)), [following])

  const visibleFeed = useMemo(
    () => feed.filter((item) => followingIds.has(item.user.id)),
    [feed, followingIds],
  )

  const visibleCompatibility = useMemo(
    () => social?.compatibility.filter((c) => followingIds.has(c.user.id)) ?? [],
    [social, followingIds],
  )

  const handleToggleFollow = useCallback((userId: string) => {
    setFollowing((prevFollowing) => {
      const existing = prevFollowing.find((u) => u.id === userId)
      if (existing) {
        setSuggestions((prevSuggestions) => [
          ...prevSuggestions,
          { ...existing, isFollowing: false },
        ])
        return prevFollowing.filter((u) => u.id !== userId)
      }
      return prevFollowing
    })

    setSuggestions((prevSuggestions) => {
      const user = prevSuggestions.find((u) => u.id === userId)
      if (!user) return prevSuggestions

      setFollowing((prevFollowing) => [...prevFollowing, { ...user, isFollowing: true }])
      return prevSuggestions.filter((u) => u.id !== userId)
    })
  }, [])

  const handleToggleLike = useCallback((id: string) => {
    setInbox((prev) =>
      prev.map((r) => (r.id === id ? { ...r, liked: !r.liked } : r)),
    )
  }, [])

  const mutualCount = following.filter((u) => u.followsYou).length

  return (
    <div className="relative min-h-screen bg-background text-foreground">
      <AuroraBackground className="fixed" />

      <div className="relative z-10">
        <Topbar user={stats?.user} />

        <main className="mx-auto max-w-6xl px-5 py-8 md:px-8 md:py-12">
          <motion.div
            initial={{ opacity: 0, y: 16 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6, ease: easeOut }}
            className="mb-8 flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between"
          >
            <div>
              <span className="inline-flex items-center gap-2 rounded-full border border-primary/30 bg-primary/10 px-3 py-1 text-xs font-medium text-primary">
                <Users className="h-3.5 w-3.5" />
                Comunidad musical
              </span>
              <h1 className="mt-4 text-balance font-heading text-3xl font-bold tracking-tight md:text-4xl">
                Tu círculo sonoro
              </h1>
              <p className="mt-2 max-w-xl text-sm text-muted-foreground md:text-base">
                Descubre qué escuchan tus amigos, compara gustos y comparte canciones con quien
                realmente conecta contigo.
              </p>
            </div>

            {!isLoading && social ? (
              <RecommendTrack friends={following} tracks={stats?.topTracks ?? []} />
            ) : null}
          </motion.div>

          {/* Stats rápidos */}
          {!isLoading && social ? (
            <motion.div
              initial={{ opacity: 0, y: 12 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.1, ease: easeOut }}
              className="mb-6 grid grid-cols-2 gap-3 md:grid-cols-4 md:gap-4"
            >
              <QuickStat label="Siguiendo" value={following.length} />
              <QuickStat label="Seguidores mutuos" value={mutualCount} />
              <QuickStat
                label="Mejor match"
                value={`${visibleCompatibility[0]?.score ?? 0}%`}
              />
              <QuickStat
                label="Recomendaciones"
                value={inbox.filter((r) => !r.liked).length}
                suffix=" nuevas"
              />
            </motion.div>
          ) : null}

          {isLoading || !social ? (
            <SocialSkeleton />
          ) : (
            <div className="flex flex-col gap-4 md:gap-6">
              <div className="grid grid-cols-1 gap-4 md:gap-6 lg:grid-cols-3">
                <div className="lg:col-span-2">
                  <ActivityFeed items={visibleFeed} />
                </div>
                <div className="flex flex-col gap-4 md:gap-6">
                  <FollowingPanel
                    following={following}
                    suggestions={suggestions}
                    onToggleFollow={handleToggleFollow}
                  />
                  <CompatibilityPanel items={visibleCompatibility} />
                </div>
              </div>

              <RecommendationsInbox items={inbox} onToggleLike={handleToggleLike} />
            </div>
          )}
        </main>

        <footer className="relative z-10 px-6 py-8 text-center text-xs text-muted-foreground">
          Actividad de tu comunidad musical en EchoTrace.
        </footer>
      </div>
    </div>
  )
}

function QuickStat({
  label,
  value,
  suffix = "",
}: {
  label: string
  value: string | number
  suffix?: string
}) {
  return (
    <div className="rounded-2xl border border-border bg-card px-4 py-3 md:px-5 md:py-4">
      <p className="text-xs text-muted-foreground">{label}</p>
      <p className="mt-1 font-heading text-2xl font-bold tracking-tight">
        {value}
        {suffix ? (
          <span className="text-base font-normal text-muted-foreground">{suffix}</span>
        ) : null}
      </p>
    </div>
  )
}

function SocialSkeleton() {
  return (
    <div className="flex flex-col gap-4 md:gap-6">
      <div className="grid grid-cols-2 gap-3 md:grid-cols-4">
        {Array.from({ length: 4 }).map((_, i) => (
          <div key={i} className="h-20 animate-pulse rounded-2xl border border-border bg-card" />
        ))}
      </div>
      <div className="grid grid-cols-1 gap-4 md:gap-6 lg:grid-cols-3">
        <div className="h-[520px] animate-pulse rounded-2xl border border-border bg-card lg:col-span-2" />
        <div className="h-[520px] animate-pulse rounded-2xl border border-border bg-card" />
      </div>
      <div className="h-64 animate-pulse rounded-2xl border border-border bg-card" />
    </div>
  )
}

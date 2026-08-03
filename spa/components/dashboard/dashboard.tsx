"use client"

import { useState } from "react"
import { motion } from "motion/react"
import { Sparkles } from "lucide-react"
import { AuroraBackground } from "@/components/aurora-background"
import { Topbar } from "@/components/dashboard/topbar"
import { StatCards } from "@/components/dashboard/stat-cards"
import { TopTracks } from "@/components/dashboard/top-tracks"
import { TopArtists } from "@/components/dashboard/top-artists"
import { RecentlyPlayed } from "@/components/dashboard/recently-played"
import { SpotifyPlayer } from "@/components/dashboard/spotify-player"
import { BreakthroughArtist } from "@/components/dashboard/breakthrough-artist"
import { TopAlbums } from "@/components/dashboard/top-albums"
import { useStats } from "@/lib/hooks/use-stats"
import type { TimeRange } from "@/lib/spotify/types"
import { useTranslations } from "next-intl"

const easeOut = [0.22, 1, 0.36, 1] as const

export function Dashboard() {
  const [timeRange, setTimeRange] = useState<TimeRange>("medium_term")
  const { stats, isLoading } = useStats(timeRange)
  const t = useTranslations()

  return (
    <div className="relative min-h-screen bg-gradient-to-br from-background via-background to-background/95 text-foreground">
      <AuroraBackground className="fixed opacity-40" />

      <div className="relative z-10">
        <Topbar user={stats?.user} timeRange={timeRange} onTimeRangeChange={setTimeRange} />

        <main className="mx-auto max-w-7xl px-5 py-8 md:px-8 md:py-12">
          {/* Saludo */}
          <motion.div
            initial={{ opacity: 0, y: 16 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6, ease: easeOut }}
            className="mb-8"
          >
            <span className="inline-flex items-center gap-2 rounded-full border border-primary/30 bg-primary/10 px-3 py-1 text-xs font-medium text-primary backdrop-blur-sm">
              <Sparkles className="h-3.5 w-3.5" />
                {t("dashboard.live")}
            </span>
            <h1 className="mt-4 text-balance font-heading text-3xl font-bold tracking-tight md:text-4xl bg-gradient-to-r from-foreground to-foreground/70 bg-clip-text text-transparent">
              {stats ? (
                <>
                    {t("dashboard.saludo")}, {stats.user.name.split(" ")[0]}.{" "}
                  <span className="text-muted-foreground">{t("dashboard.saludo2")}</span>
                </>
              ) : (
                t('dashboard.cargando')
              )}
            </h1>
          </motion.div>

          {isLoading || !stats ? (
            <DashboardSkeleton />
          ) : (
            <div className="flex flex-col gap-5 md:gap-7">
              <SpotifyPlayer />
              <StatCards totals={stats.totals} user={stats.user} />

              <div className="grid grid-cols-1 gap-5 md:gap-7 lg:grid-cols-2">
                <TopTracks tracks={stats.topTracks} timeRange={timeRange} />
                <div className="flex flex-col gap-5 md:gap-7">
                  <BreakthroughArtist artist={stats.breakthroughArtist == null ?
                      stats.topArtists[0] : stats.breakthroughArtist} top={stats.breakthroughArtist === null} />
                  <TopAlbums albums={stats.topAlbums} />
                </div>
              </div>

              <TopArtists artists={stats.topArtists} timeRange={timeRange} />
              <RecentlyPlayed items={stats.recentlyPlayed} />
            </div>
          )}
        </main>

        <footer className="relative z-10 px-6 py-8 text-center text-xs text-muted-foreground/70">
            {t("dashboard.disclaimer")}
        </footer>
      </div>
    </div>
  )
}

function DashboardSkeleton() {
  return (
    <div className="flex flex-col gap-5 md:gap-7">
      <div className="h-24 animate-pulse rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm" />
      <div className="grid grid-cols-2 gap-3 md:grid-cols-4 md:gap-4">
        {Array.from({ length: 4 }).map((_, i) => (
          <div key={i} className="h-32 animate-pulse rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm" />
        ))}
      </div>
      <div className="grid grid-cols-1 gap-5 md:gap-7 lg:grid-cols-2">
        <div className="h-96 animate-pulse rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm" />
        <div className="flex flex-col gap-5 md:gap-7">
          <div className="h-32 animate-pulse rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm" />
          <div className="h-64 animate-pulse rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm" />
        </div>
      </div>
      <div className="h-72 animate-pulse rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm" />
      <div className="h-48 animate-pulse rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm" />
    </div>
  )
}

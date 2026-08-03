"use client"

import { useState } from "react"
import Image from "next/image"
import { motion } from "motion/react"
import { Play, ChevronLeft, ChevronRight, TrendingUp, TrendingDown, Minus } from "lucide-react"
import { Equalizer } from "@/components/equalizer"
import { usePlayer } from "@/lib/hooks/use-player"
import type { Track } from "@/lib/spotify/types"
import { formatDuration } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { useTranslations } from "next-intl"

const easeOut = [0.22, 1, 0.36, 1] as const

interface TopTracksProps {
  tracks: Track[]
  timeRange?: string
}

export function TopTracks({ tracks, timeRange }: TopTracksProps) {
  const { play, refresh } = usePlayer()
  const [visibleCount, setVisibleCount] = useState(10)
  const [currentPage, setCurrentPage] = useState(0)
  const itemsPerPage = 10

  const totalPages = Math.ceil(tracks.length / itemsPerPage)
  const startIndex = currentPage * itemsPerPage
  const endIndex = Math.min(startIndex + itemsPerPage, tracks.length)
  const currentTracks = tracks.slice(startIndex, endIndex)
  const t = useTranslations()

  const handlePlay = async (track: Track) => {
    if (track.url) {
      await play(track.url)
      // Esperar 2 segundos antes de refrescar para dar tiempo a la API
      setTimeout(async () => {
        await refresh() // Actualizar estado del player después de reproducir
      }, 1000)
    }
  }

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1)
    }
  }

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1)
    }
  }

  const renderRankChange = (track: Track) => {
    if (timeRange !== "short_term") return null

    if (track.isNew) {
      return (
        <div className="flex items-center gap-1 text-xs">
          <div className="h-2 w-2 rounded-full bg-blue-500" />
          <span className="text-blue-500">{t("dashboard.tracks.rank.nuevo")}</span>
        </div>
      )
    }

    if (track.rankChange === undefined) return null

    if (track.rankChange === 0) {
      return (
        <div className="flex items-center gap-1 text-xs text-muted-foreground">
          <Minus className="h-3 w-3" />
        </div>
      )
    }

    if (track.rankChange > 0) {
      return (
        <div className="flex items-center gap-1 text-xs text-green-500">
          <TrendingUp className="h-3 w-3" />
          <span>+{track.rankChange}</span>
        </div>
      )
    }

    return (
      <div className="flex items-center gap-1 text-xs text-red-500">
        <TrendingDown className="h-3 w-3" />
        <span>{track.rankChange}</span>
      </div>
    )
  }

  return (
    <section className="rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm p-5 md:p-6 shadow-lg shadow-primary/5">
      <SectionHeader
        title={t("dashboard.tracks.title")}
        subtitle={t("dashboard.tracks.sub")}
        showPagination={totalPages > 1}
        currentPage={currentPage}
        totalPages={totalPages}
        onNextPage={handleNextPage}
        onPreviousPage={handlePreviousPage}
      />
      <ul className="mt-5 flex flex-col gap-1">
        {currentTracks.map((track, i) => (
          <motion.li
            key={track.id}
            initial={{ opacity: 0, x: -12 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.4, delay: i * 0.04, ease: easeOut }}
            className="group flex items-center gap-3 rounded-xl px-2 py-2 transition-all hover:bg-secondary/50 hover:shadow-md"
          >
            <span className="w-5 shrink-0 text-center font-mono text-sm text-muted-foreground">
              {startIndex + i + 1}
            </span>
            <div className="relative h-11 w-11 shrink-0 overflow-hidden rounded-lg">
              <Image
                src={track.album.images?.[0]?.url || "/covers/cover-1.png"}
                alt={track.album.name}
                fill
                sizes="44px"
                className="object-cover"
              />
              <button
                onClick={() => handlePlay(track)}
                className="absolute inset-0 hidden items-center justify-center bg-background/55 group-hover:flex hover:bg-background/70 transition-colors"
              >
                <Play className="h-4 w-4 fill-primary text-primary" />
              </button>
            </div>
            <div className="min-w-0 flex-1">
              <p className="truncate text-sm font-medium">{track.name}</p>
              <p className="truncate text-xs text-muted-foreground">
                {track.artists.map((a) => a.name).join(", ")}
              </p>
            </div>
            {renderRankChange(track)}
            <span className="w-10 shrink-0 text-right font-mono text-xs text-muted-foreground">
              {formatDuration(track.durationMs)}
            </span>
          </motion.li>
        ))}
      </ul>
    </section>
  )
}

interface SectionHeaderProps {
  title: string
  subtitle?: string
  showPagination?: boolean
  currentPage?: number
  totalPages?: number
  onNextPage?: () => void
  onPreviousPage?: () => void
}

export function SectionHeader({
  title,
  subtitle,
  showPagination,
  currentPage = 0,
  totalPages = 1,
  onNextPage,
  onPreviousPage,
}: SectionHeaderProps) {
  return (
    <div className="flex items-end justify-between gap-3">
      <div>
        <h2 className="font-heading text-lg font-bold tracking-tight md:text-xl">{title}</h2>
        {subtitle ? <p className="text-xs text-muted-foreground md:text-sm">{subtitle}</p> : null}
      </div>
      {showPagination && (
        <div className="flex items-center gap-1">
          <Button
            variant="ghost"
            size="icon"
            className="h-7 w-7"
            onClick={onPreviousPage}
            disabled={currentPage === 0}
          >
            <ChevronLeft className="h-4 w-4" />
          </Button>
          <span className="text-xs text-muted-foreground px-2">
            {currentPage + 1} / {totalPages}
          </span>
          <Button
            variant="ghost"
            size="icon"
            className="h-7 w-7"
            onClick={onNextPage}
            disabled={currentPage === totalPages - 1}
          >
            <ChevronRight className="h-4 w-4" />
          </Button>
        </div>
      )}
    </div>
  )
}

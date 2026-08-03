"use client"

import { useState } from "react"
import Image from "next/image"
import { motion } from "motion/react"
import { ChevronLeft, ChevronRight, TrendingUp, TrendingDown, Minus, Plus } from "lucide-react"
import { SectionHeader } from "@/components/dashboard/top-tracks"
import type { Artist } from "@/lib/spotify/types"
import { formatNumber } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { useTranslations } from "next-intl"

const easeOut = [0.22, 1, 0.36, 1] as const

interface TopArtistsProps {
  artists: Artist[]
  timeRange?: string
}

export function TopArtists({ artists, timeRange }: TopArtistsProps) {
  const [visibleCount, setVisibleCount] = useState(6)
  const [currentPage, setCurrentPage] = useState(0)
  const itemsPerPage = 9

  const totalPages = Math.ceil(artists.length / itemsPerPage)
  const startIndex = currentPage * itemsPerPage
  const endIndex = Math.min(startIndex + itemsPerPage, artists.length)
  const currentArtists = artists.slice(startIndex, endIndex)
  const t = useTranslations()

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

  const renderRankChange = (artist: Artist) => {
    if (timeRange !== "short_term") return null

    if (artist.isNew) {
      return (
        <div className="flex items-center gap-1 text-xs">
          <div className="h-2 w-2 rounded-full bg-blue-500" />
          <span className="text-blue-500">{t("dashboard.artists.rank.nuevo")}</span>
        </div>
      )
    }

    if (artist.rankChange === undefined) return null

    if (artist.rankChange === 0) {
      return (
        <div className="flex items-center gap-1 text-xs text-muted-foreground">
          <Minus className="h-3 w-3" />
        </div>
      )
    }

    if (artist.rankChange > 0) {
      return (
        <div className="flex items-center gap-1 text-xs text-green-500">
          <TrendingUp className="h-3 w-3" />
          <span>+{artist.rankChange}</span>
        </div>
      )
    }

    return (
      <div className="flex items-center gap-1 text-xs text-red-500">
        <TrendingDown className="h-3 w-3" />
        <span>{artist.rankChange}</span>
      </div>
    )
  }

  return (
    <section className="rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm p-5 md:p-6 shadow-lg shadow-primary/5">
      <SectionHeader
        title={t("dashboard.artists.title")}
        subtitle={t("dashboard.artists.sub")}
        showPagination={totalPages > 1}
        currentPage={currentPage}
        totalPages={totalPages}
        onNextPage={handleNextPage}
        onPreviousPage={handlePreviousPage}
      />
      <div className="mt-5 grid grid-cols-2 gap-3 sm:grid-cols-3">
        {currentArtists.map((artist, i) => (
          <motion.div
            key={artist.id}
            initial={{ opacity: 0, scale: 0.92 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.45, delay: i * 0.05, ease: easeOut }}
            className="group relative overflow-hidden rounded-2xl border border-border/50 shadow-md hover:shadow-xl transition-all duration-300"
          >
            <div className="relative aspect-square">
              <Image
                src={artist.images?.[0]?.url || "/artists/artist-1.png"}
                alt={artist.name}
                fill
                sizes="(max-width: 640px) 50vw, 200px"
                className="object-cover transition-transform duration-500 group-hover:scale-110"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-background via-background/40 to-transparent" />
              <span className="absolute left-2 top-2 flex h-6 w-6 items-center justify-center rounded-full bg-primary/90 text-xs font-bold text-primary-foreground">
                {startIndex + i + 1}
              </span>
            </div>
            <div className="absolute inset-x-0 bottom-0 p-3">
              <p className="truncate text-sm font-semibold">{artist.name}</p>
              {renderRankChange(artist)}
            </div>
          </motion.div>
        ))}
      </div>
    </section>
  )
}

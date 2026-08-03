"use client"

import { useState } from "react"
import Image from "next/image"
import { motion, AnimatePresence } from "motion/react"
import { ChevronDown, Disc3, Sparkles } from "lucide-react"
import type { Compatibility } from "@/lib/spotify/types"

const easeOut = [0.22, 1, 0.36, 1] as const

interface CompatibilityPanelProps {
  items: Compatibility[]
}

export function CompatibilityPanel({ items }: CompatibilityPanelProps) {
  const [expandedId, setExpandedId] = useState<string | null>(items[0]?.user.id ?? null)
  const topMatch = items[0]

  return (
    <section className="rounded-2xl border border-border bg-card p-5 md:p-6">
      <div className="mb-5">
        <h2 className="font-heading text-lg font-bold tracking-tight md:text-xl">
          Compatibilidad musical
        </h2>
        <p className="text-xs text-muted-foreground md:text-sm">
          Cuánto coincidís en gustos con quien sigues
        </p>
      </div>

      {topMatch ? (
        <motion.div
          initial={{ opacity: 0, scale: 0.98 }}
          animate={{ opacity: 1, scale: 1 }}
          className="mb-5 overflow-hidden rounded-xl border border-primary/25 bg-gradient-to-br from-primary/15 via-card to-card p-4"
        >
          <div className="flex items-center gap-2 text-xs font-medium text-primary">
            <Sparkles className="h-3.5 w-3.5" />
            Mejor match
          </div>
          <div className="mt-3 flex items-center gap-4">
            <ScoreRing score={topMatch.score} size={72} />
            <div>
              <p className="font-heading text-lg font-bold">{topMatch.user.display_name}</p>
              <p className="text-xs text-muted-foreground">
                {topMatch.sharedTracks} canciones en común · {topMatch.sharedGenres.length} géneros
              </p>
            </div>
          </div>
        </motion.div>
      ) : null}

      <ul className="flex flex-col gap-2">
        {items.map((item, i) => (
          <CompatibilityRow
            key={item.user.id}
            item={item}
            index={i}
            expanded={expandedId === item.user.id}
            onToggle={() =>
              setExpandedId((id) => (id === item.user.id ? null : item.user.id))
            }
          />
        ))}
      </ul>
    </section>
  )
}

function CompatibilityRow({
  item,
  index,
  expanded,
  onToggle,
}: {
  item: Compatibility
  index: number
  expanded: boolean
  onToggle: () => void
}) {
  return (
    <motion.li
      initial={{ opacity: 0, x: -8 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.3, delay: index * 0.04, ease: easeOut }}
      className="overflow-hidden rounded-xl border border-border/60 bg-secondary/20"
    >
      <button
        type="button"
        onClick={onToggle}
        className="flex w-full items-center gap-3 p-3 text-left transition-colors hover:bg-secondary/40"
      >
        <Image
          src={item.user.images[0]?.url || "/avatar.png"}
          alt={item.user.display_name}
          width={36}
          height={36}
          className="h-9 w-9 rounded-full object-cover"
        />
        <div className="min-w-0 flex-1">
          <p className="truncate text-sm font-medium">{item.user.display_name}</p>
          <p className="text-xs text-muted-foreground">{item.user.topGenre}</p>
        </div>
        <ScoreRing score={item.score} size={44} />
        <ChevronDown
          className={`h-4 w-4 shrink-0 text-muted-foreground transition-transform ${
            expanded ? "rotate-180" : ""
          }`}
        />
      </button>

      <AnimatePresence>
        {expanded ? (
          <motion.div
            initial={{ height: 0, opacity: 0 }}
            animate={{ height: "auto", opacity: 1 }}
            exit={{ height: 0, opacity: 0 }}
            transition={{ duration: 0.25, ease: easeOut }}
            className="overflow-hidden border-t border-border/60"
          >
            <div className="space-y-4 p-4 pt-3">
              <div>
                <p className="mb-2 text-xs font-medium text-muted-foreground">
                  Artistas en común
                </p>
                <div className="flex flex-wrap gap-2">
                  {item.sharedArtists.map((artist) => (
                    <span
                      key={artist.id}
                      className="inline-flex items-center gap-1.5 rounded-full border border-border bg-background/60 px-2.5 py-1 text-xs"
                    >
                      <Image
                        src={artist.images[0]?.url || "/artists/artist-1.png"}
                        alt={artist.name}
                        width={16}
                        height={16}
                        className="h-4 w-4 rounded-full object-cover"
                      />
                      {artist.name}
                    </span>
                  ))}
                </div>
              </div>

              <div>
                <p className="mb-2 text-xs font-medium text-muted-foreground">
                  Géneros compartidos
                </p>
                <div className="flex flex-wrap gap-1.5">
                  {item.sharedGenres.map((genre) => (
                    <span
                      key={genre}
                      className="rounded-full bg-primary/10 px-2 py-0.5 text-[11px] font-medium text-primary"
                    >
                      {genre}
                    </span>
                  ))}
                </div>
              </div>

              <div className="flex items-center gap-2 text-xs text-muted-foreground">
                <Disc3 className="h-3.5 w-3.5" />
                {item.sharedTracks} canciones que ambos habéis escuchado mucho
              </div>
            </div>
          </motion.div>
        ) : null}
      </AnimatePresence>
    </motion.li>
  )
}

function ScoreRing({ score, size }: { score: number; size: number }) {
  const stroke = 3
  const radius = (size - stroke) / 2
  const circumference = 2 * Math.PI * radius
  const offset = circumference - (score / 100) * circumference

  return (
    <div className="relative shrink-0" style={{ width: size, height: size }}>
      <svg width={size} height={size} className="-rotate-90">
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          fill="none"
          stroke="currentColor"
          strokeWidth={stroke}
          className="text-border"
        />
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          fill="none"
          stroke="currentColor"
          strokeWidth={stroke}
          strokeDasharray={circumference}
          strokeDashoffset={offset}
          strokeLinecap="round"
          className="text-primary transition-all duration-700"
        />
      </svg>
      <span
        className="absolute inset-0 flex items-center justify-center font-mono text-xs font-bold"
        style={{ fontSize: size > 50 ? 14 : 11 }}
      >
        {score}%
      </span>
    </div>
  )
}

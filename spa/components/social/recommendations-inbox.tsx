"use client"

import Image from "next/image"
import { motion } from "motion/react"
import { Heart, Inbox, Play } from "lucide-react"
import type { Recommendation } from "@/lib/spotify/types"

const easeOut = [0.22, 1, 0.36, 1] as const

interface RecommendationsInboxProps {
  items: Recommendation[]
  onToggleLike: (id: string) => void
}

export function RecommendationsInbox({ items, onToggleLike }: RecommendationsInboxProps) {
  const unread = items.filter((r) => !r.liked).length

  return (
    <section className="rounded-2xl border border-border bg-card p-5 md:p-6">
      <div className="mb-5 flex items-start justify-between gap-3">
        <div>
          <h2 className="font-heading text-lg font-bold tracking-tight md:text-xl">
            Bandeja de recomendaciones
          </h2>
          <p className="text-xs text-muted-foreground md:text-sm">
            Música que tus amigos creen que deberías escuchar
          </p>
        </div>
        {unread > 0 ? (
          <span className="inline-flex items-center gap-1 rounded-full bg-primary/15 px-2.5 py-1 text-xs font-medium text-primary">
            <Inbox className="h-3 w-3" />
            {unread} nueva{unread > 1 ? "s" : ""}
          </span>
        ) : null}
      </div>

      <ul className="grid grid-cols-1 gap-3 md:grid-cols-2 lg:grid-cols-3">
        {items.map((rec, i) => (
          <motion.li
            key={rec.id}
            initial={{ opacity: 0, y: 12 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.35, delay: i * 0.06, ease: easeOut }}
            className={`group relative overflow-hidden rounded-xl border p-4 transition-colors ${
              rec.liked
                ? "border-primary/20 bg-primary/5"
                : "border-border bg-secondary/20 hover:border-primary/30"
            }`}
          >
            <div className="mb-3 flex items-center gap-2">
              <Image
                src={rec.from.images[0]?.url || "/avatar.png"}
                alt={rec.from.display_name}
                width={24}
                height={24}
                className="h-6 w-6 rounded-full object-cover"
              />
              <div className="min-w-0">
                <p className="truncate text-xs font-medium">{rec.from.display_name}</p>
                <p className="text-[10px] text-muted-foreground">{rec.timeAgo}</p>
              </div>
            </div>

            <div className="flex gap-3">
              <div className="relative h-14 w-14 shrink-0 overflow-hidden rounded-lg">
                <Image
                  src={rec.track.album.images?.[0]?.url || "/covers/cover-1.png"}
                  alt={rec.track.name}
                  fill
                  sizes="56px"
                  className="object-cover"
                />
                <span className="absolute inset-0 flex items-center justify-center bg-background/50 opacity-0 transition-opacity group-hover:opacity-100">
                  <Play className="h-5 w-5 fill-primary text-primary" />
                </span>
              </div>
              <div className="min-w-0 flex-1">
                <p className="truncate text-sm font-semibold">{rec.track.name}</p>
                <p className="truncate text-xs text-muted-foreground">
                  {rec.track.artists.map((a) => a.name).join(", ")}
                </p>
              </div>
            </div>

            <p className="mt-3 line-clamp-2 text-xs italic text-muted-foreground">
              &ldquo;{rec.note}&rdquo;
            </p>

            <div className="mt-3 flex items-center justify-between">
              <button
                type="button"
                onClick={() => onToggleLike(rec.id)}
                className={`inline-flex items-center gap-1.5 rounded-full px-3 py-1.5 text-xs font-medium transition-colors ${
                  rec.liked
                    ? "bg-primary text-primary-foreground"
                    : "border border-border text-muted-foreground hover:border-primary/40 hover:text-primary"
                }`}
              >
                <Heart className={`h-3.5 w-3.5 ${rec.liked ? "fill-current" : ""}`} />
                {rec.liked ? "Me gusta" : "Guardar"}
              </button>
            </div>
          </motion.li>
        ))}
      </ul>
    </section>
  )
}

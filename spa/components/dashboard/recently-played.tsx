"use client"

import Image from "next/image"
import { motion } from "motion/react"
import { Play } from "lucide-react"
import { SectionHeader } from "@/components/dashboard/top-tracks"
import { usePlayer } from "@/lib/hooks/use-player"
import type { RecentlyItem, Track } from "@/lib/spotify/types"
import { useTranslations } from "next-intl"

const easeOut = [0.22, 1, 0.36, 1] as const


export function RecentlyPlayed({ items }: { items: RecentlyItem[] }) {
  const t = useTranslations()
  const { play, refresh } = usePlayer()

  const formatRelativeTime = (dateString: string) => {
    const date = new Date(dateString)
    const now = new Date()
    const diffMs = now.getTime() - date.getTime()
    const diffMins = Math.floor(diffMs / 60000)
    const diffHours = Math.floor(diffMs / 3600000)
    const diffDays = Math.floor(diffMs / 86400000)

    if (diffMins < 1) return t("dashboard.recently.ahora")
    if (diffMins < 60) return t("dashboard.recently.tiempo.minutos", { count: diffMins });
    if (diffHours < 24) return t("dashboard.recently.tiempo.horas", { count: diffHours });
    if (diffDays < 7) return t("dashboard.recently.tiempo.dias", { count: diffDays });
    return date.toLocaleDateString("es-ES", { day: "numeric", month: "short" })
  }

  const handlePlay = async (track: Track) => {
    if (track.url) {
      await play(track.url)
      // Esperar 2 segundos antes de refrescar para dar tiempo a la API
      setTimeout(async () => {
        await refresh() // Actualizar estado del player después de reproducir
      }, 2000)
    }
  }

  return (
    <section className="rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm p-5 md:p-6 shadow-lg shadow-primary/5">
      <SectionHeader title={t("dashboard.recently.title")} subtitle={t("dashboard.recently.subtitle")} />
      <div className="mt-5 flex gap-4 overflow-x-auto pb-2 [scrollbar-width:none] [&::-webkit-scrollbar]:hidden">
        {items.map((item, i) => (
          <motion.div
            key={`${item.track.id}-${i}`}
            initial={{ opacity: 0, y: 16 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.45, delay: i * 0.05, ease: easeOut }}
            className="group w-36 shrink-0"
          >
            <div className="relative aspect-square overflow-hidden rounded-xl border border-border">
              <Image
                src={item.track.album.images?.[0]?.url || "/covers/cover-1.png"}
                alt={item.track.album.name}
                fill
                sizes="144px"
                className="object-cover transition-transform duration-500 group-hover:scale-110"
              />
              <button
                onClick={() => handlePlay(item.track)}
                className="absolute inset-0 hidden items-center justify-center bg-background/55 group-hover:flex hover:bg-background/70 transition-colors"
              >
                <Play className="h-6 w-6 fill-primary text-primary" />
              </button>
            </div>
            <p className="mt-2 truncate text-sm font-medium">{item.track.name}</p>
            <p className="truncate text-xs text-muted-foreground">
              {item.track.artists.map((a) => a.name).join(", ")}
            </p>
            <p className="mt-0.5 text-xs text-primary">{formatRelativeTime(item.playedAt)}</p>
          </motion.div>
        ))}
      </div>
    </section>
  )
}

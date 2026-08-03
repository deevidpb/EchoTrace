"use client"

import Image from "next/image"
import { motion } from "motion/react"
import { Sparkles, TrendingUp } from "lucide-react"
import type { Artist } from "@/lib/spotify/types"
import { useTranslations } from "next-intl"

const easeOut = [0.22, 1, 0.36, 1] as const


export function BreakthroughArtist({ artist, top }: { artist: Artist | null, top: boolean}) {
  const t  = useTranslations()

  if (artist === null) {
    return (
      <section className="rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm p-5 md:p-6">
        <div className="flex items-center gap-3 mb-4">
          <Sparkles className="h-5 w-5 text-primary" />
          <h2 className="text-lg font-semibold">Tu Top 1</h2>
        </div>
        <p className="text-sm text-muted-foreground/70">{t("dashboard.break.noData")}</p>
      </section>
    )
  }

  if (top){
    return (
        <motion.section
            initial={{ opacity: 0, y: 16 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.45, ease: easeOut }}
            className="rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm p-5 md:p-6 shadow-lg shadow-primary/5"
        >
          <div className="flex items-center gap-3 mb-4">
            <Sparkles className="h-5 w-5 text-primary" />
            <h2 className="text-lg font-semibold">{t("dashboard.break.top1")}</h2>
          </div>

          <div className="flex items-center gap-4">
            <div className="relative h-20 w-20 shrink-0 overflow-hidden rounded-xl">
              <Image
                  src={artist.images?.[0]?.url || "/covers/cover-1.png"}
                  alt={artist.name}
                  fill
                  sizes="80px"
                  className="object-cover"
              />
            </div>

            <div className="min-w-0 flex-1">
              <h3 className="text-lg font-semibold truncate">{artist.name}</h3>
              <p className="text-sm text-muted-foreground flex items-center gap-1">
                <TrendingUp className="h-4 w-4 text-green-500" />
                {t("dashboard.break.escuchado")}
              </p>
            </div>
          </div>
        </motion.section>
    )
  }

  return (
    <motion.section
      initial={{ opacity: 0, y: 16 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.45, ease: easeOut }}
      className="rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm p-5 md:p-6 shadow-lg shadow-primary/5"
    >
      <div className="flex items-center gap-3 mb-4">
        <Sparkles className="h-5 w-5 text-primary" />
        <h2 className="text-lg font-semibold">{artist.isNew? t("dashboard.break.revelacion") : t("dashboard.break.subida")}</h2>
      </div>

      <div className="flex items-center gap-4">
        <div className="relative h-20 w-20 shrink-0 overflow-hidden rounded-xl">
          <Image
            src={artist.images?.[0]?.url || "/covers/cover-1.png"}
            alt={artist.name}
            fill
            sizes="80px"
            className="object-cover"
          />
        </div>

        <div className="min-w-0 flex-1">
          <h3 className="text-lg font-semibold truncate">{artist.name}</h3>
          <p className="text-sm text-muted-foreground flex items-center gap-1">
            <TrendingUp className="h-4 w-4 text-green-500" />
            {t("dashboard.break.top")}
          </p>
        </div>
      </div>
    </motion.section>
  )
}

"use client"

import Image from "next/image"
import { motion } from "motion/react"
import { Disc } from "lucide-react"
import type { Album } from "@/lib/spotify/types"
import { useTranslations } from "next-intl"

const easeOut = [0.22, 1, 0.36, 1] as const

export function TopAlbums({ albums }: { albums: Album[] }) {
    const t = useTranslations()

    return (
    <section className="rounded-2xl border border-border/50 bg-card/50 backdrop-blur-sm p-5 md:p-6 shadow-lg shadow-primary/5">
      <div className="flex items-center gap-3 mb-4">
        <Disc className="h-5 w-5 text-primary" />
        <h2 className="text-lg font-semibold">{t("dashboard.albums.titulo")}</h2>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
        {albums.slice(0, 6).map((album, i) => (
          <motion.div
            key={album.id}
            initial={{ opacity: 0, y: 16 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.45, delay: i * 0.05, ease: easeOut }}
            className="group"
          >
            <div className="relative aspect-square overflow-hidden rounded-xl border border-border">
              <Image
                src={album.images?.[0]?.url || "/covers/cover-1.png"}
                alt={album.name}
                fill
                sizes="(max-width: 768px) 50vw, 33vw"
                className="object-cover transition-transform duration-500 group-hover:scale-110"
              />
            </div>
            <p className="mt-2 truncate text-sm font-medium">{album.name}</p>
            <p className="truncate text-xs text-muted-foreground">
              {album.artists.map((a) => a.name).join(", ")}
            </p>
          </motion.div>
        ))}
      </div>
    </section>
    )
}

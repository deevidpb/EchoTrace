"use client"

import { useState, useEffect } from "react"
import Image from "next/image"
import { motion, AnimatePresence } from "motion/react"
import { Play, Pause, SkipBack, SkipForward, Music } from "lucide-react"
import { usePlayer } from "@/lib/hooks/use-player"
import { Button } from "@/components/ui/button"
import { useTranslations } from "next-intl"

const easeOut = [0.22, 1, 0.36, 1] as const

export function SpotifyPlayer() {
  const t = useTranslations()
  const { playback, isLoading, resume, pause, next, previous, refresh } = usePlayer()
  const [showPause, setShowPause] = useState(true) // Siempre empieza en true (Pause)
  const [currentTrackId, setCurrentTrackId] = useState<string | null>(null)

  // Detectar nueva canción y resetear botón a Pause
  useEffect(() => {
    if (playback?.track?.id && playback.track.id !== currentTrackId) {
      setCurrentTrackId(playback.track.id)
      setShowPause(true) // Resetear a Pause cuando cambia la canción
    }
  }, [playback?.track?.id, currentTrackId])

  const formatTime = (ms: number) => {
    const minutes = Math.floor(ms / 60000)
    const seconds = Math.floor((ms % 60000) / 1000)
    return `${minutes}:${seconds.toString().padStart(2, "0")}`
  }

  const handlePlayPause = async () => {
    if (!playback?.track) return

    try {
      if (showPause) {
        await pause()
      } else {
        await resume()
      }

      // Si no hay error, cambiar el estado local
      setShowPause(!showPause)

      // Esperar 2 segundos antes de refrescar para dar tiempo a la API
      setTimeout(async () => {
        await refresh()
      }, 2000)
    } catch (error) {
      console.error("Error toggling playback:", error)
      // Si hay error, no cambiar el estado local
    }
  }

  const handleNext = async () => {
    await next()
    setTimeout(async () => {
      await refresh()
    }, 2000)
  }

  const handlePrevious = async () => {
    await previous()
    setTimeout(async () => {
      await refresh()
    }, 2000)
  }

  if (isLoading || !playback) {
    return (
      <section className="rounded-2xl border border-border/50 bg-gradient-to-br from-card/80 to-card/50 backdrop-blur-xl p-4 shadow-xl shadow-primary/10">
        <div className="flex items-center gap-4">
          <div className="h-16 w-16 animate-pulse rounded-xl bg-muted/50" />
          <div className="flex-1 space-y-2">
            <div className="h-4 w-3/4 animate-pulse rounded bg-muted/50" />
            <div className="h-3 w-1/2 animate-pulse rounded bg-muted/50" />
          </div>
        </div>
      </section>
    )
  }

  if (!playback.track) {
    return (
      <section className="rounded-2xl border border-border/50 bg-gradient-to-br from-card/80 to-card/50 backdrop-blur-xl p-4 shadow-xl shadow-primary/10">
        <p className="text-center text-sm text-muted-foreground/70">{t("dashboard.player.noPlay")}</p>
      </section>
    )
  }

  return (
    <motion.section
      initial={{ opacity: 0, y: 16 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.45, ease: easeOut }}
      className="rounded-2xl border border-border/50 bg-gradient-to-br from-card/80 to-card/50 backdrop-blur-xl p-4 shadow-xl shadow-primary/10"
    >
      <div className="flex items-center gap-3 mb-3">
        <Music className="h-3 w-3 text-primary" />
        <span className="text-xs font-medium text-primary">
          {showPause ? t("dashboard.player.reproduciendo"): t("dashboard.player.pausado")}
        </span>
      </div>

      <div className="flex items-center gap-4">
        {/* Album Art - Compacto */}
        <div className="relative h-16 w-16 shrink-0 overflow-hidden rounded-xl shadow-lg">
          <Image
            src={playback.track.album.images?.[0]?.url || "/covers/cover-1.png"}
            alt={playback.track.album.name}
            fill
            sizes="64px"
            className="object-cover"
          />
          {/* Animación de sonido cuando está reproduciendo */}
          <AnimatePresence>
            {showPause && (
              <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                className="absolute inset-0 bg-gradient-to-t from-black/30 to-transparent"
              >
                <div className="absolute bottom-1 left-1 right-1 flex items-end justify-center gap-0.5">
                  {[1, 2, 3, 4].map((i) => (
                    <motion.div
                      key={i}
                      className="w-0.5 bg-white/90 rounded-full"
                      animate={{
                        height: [4, 8, 4],
                      }}
                      transition={{
                        duration: 0.8,
                        repeat: Infinity,
                        delay: i * 0.1,
                      }}
                    />
                  ))}
                </div>
              </motion.div>
            )}
          </AnimatePresence>
        </div>

        {/* Track Info - Compacto */}
        <div className="min-w-0 flex-1">
          <h3 className="text-sm font-bold text-foreground truncate">{playback.track.name}</h3>
          <p className="text-xs text-muted-foreground truncate">
            {playback.track.artists.map((a) => a.name).join(", ")}
          </p>
        </div>

        {/* Controls - Compactos */}
        <div className="flex items-center gap-2">

          <Button
            variant="ghost"
            size="icon"
            className="h-8 w-8 text-muted-foreground hover:text-foreground hover:bg-secondary/50"
            onClick={handlePrevious}
          >
            <SkipBack className="h-4 w-4" />
          </Button>

          <Button
            size="icon"
            className="h-10 w-10 rounded-full bg-primary text-primary-foreground hover:bg-primary/90 shadow-lg shadow-primary/30"
            onClick={handlePlayPause}
          >
            {showPause ? (
              <Pause className="h-5 w-5 fill-current" />
            ) : (
              <Play className="h-5 w-5 fill-current ml-0.5" />
            )}
          </Button>

          <Button
            variant="ghost"
            size="icon"
            className="h-8 w-8 text-muted-foreground hover:text-foreground hover:bg-secondary/50"
            onClick={handleNext}
          >
            <SkipForward className="h-4 w-4" />
          </Button>
        </div>
      </div>
    </motion.section>
  )
}

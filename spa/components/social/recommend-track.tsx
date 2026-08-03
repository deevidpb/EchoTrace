"use client"

import { useState } from "react"
import Image from "next/image"
import { motion, AnimatePresence } from "motion/react"
import { Check, Send, X } from "lucide-react"
import { sendRecommendation } from "@/lib/api/social"
import type { SocialUser, Track } from "@/lib/spotify/types"

const easeOut = [0.22, 1, 0.36, 1] as const

interface RecommendTrackProps {
  friends: SocialUser[]
  tracks: Track[]
  onSent?: (friend: SocialUser, track: Track, note: string) => void
}

export function RecommendTrack({ friends, tracks, onSent }: RecommendTrackProps) {
  const [open, setOpen] = useState(false)
  const [selectedFriend, setSelectedFriend] = useState(friends[0]?.id ?? "")
  const [selectedTrack, setSelectedTrack] = useState(tracks[0]?.id ?? "")
  const [note, setNote] = useState("")
  const [sent, setSent] = useState(false)
  const [sending, setSending] = useState(false)

  const mutualFriends = friends.filter((f) => f.followsYou)
  const trackOptions = tracks.slice(0, 6)

  async function handleSend() {
    const friend = friends.find((f) => f.id === selectedFriend)
    const track = trackOptions.find((t) => t.id === selectedTrack)
    if (!friend || !track) return

    setSending(true)
    try {
      await sendRecommendation({
        toUserId: friend.id,
        trackId: track.id,
        note: note || "Creo que te va a gustar esto.",
      })
      onSent?.(friend, track, note || "Creo que te va a gustar esto.")
      setSent(true)
      setTimeout(() => {
        setSent(false)
        setOpen(false)
        setNote("")
      }, 1800)
    } finally {
      setSending(false)
    }
  }

  return (
    <>
      <button
        type="button"
        onClick={() => setOpen(true)}
        disabled={mutualFriends.length === 0 || trackOptions.length === 0}
        className="inline-flex items-center gap-2 rounded-full bg-primary px-5 py-2.5 text-sm font-semibold text-primary-foreground transition-transform hover:scale-[1.02] active:scale-95 disabled:opacity-50"
      >
        <Send className="h-4 w-4" />
        Recomendar canción
      </button>

      <AnimatePresence>
        {open ? (
          <>
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              className="fixed inset-0 z-40 bg-background/70 backdrop-blur-sm"
              onClick={() => !sent && setOpen(false)}
            />
            <motion.div
              initial={{ opacity: 0, scale: 0.95, y: 20 }}
              animate={{ opacity: 1, scale: 1, y: 0 }}
              exit={{ opacity: 0, scale: 0.95, y: 20 }}
              transition={{ duration: 0.25, ease: easeOut }}
              className="fixed left-1/2 top-1/2 z-50 w-[calc(100%-2rem)] max-w-md -translate-x-1/2 -translate-y-1/2 rounded-2xl border border-border bg-card p-6 shadow-2xl"
            >
              {sent ? (
                <div className="flex flex-col items-center gap-3 py-8 text-center">
                  <span className="flex h-14 w-14 items-center justify-center rounded-full bg-primary/15 text-primary">
                    <Check className="h-7 w-7" />
                  </span>
                  <p className="font-heading text-lg font-bold">¡Enviado!</p>
                  <p className="text-sm text-muted-foreground">
                    Tu amigo recibirá la recomendación en su bandeja.
                  </p>
                </div>
              ) : (
                <>
                  <div className="mb-5 flex items-start justify-between">
                    <div>
                      <h3 className="font-heading text-lg font-bold">Recomendar a un amigo</h3>
                      <p className="text-xs text-muted-foreground">
                        Solo puedes enviar a quien te sigue de vuelta
                      </p>
                    </div>
                    <button
                      type="button"
                      onClick={() => setOpen(false)}
                      className="rounded-full p-1 text-muted-foreground hover:text-foreground"
                      aria-label="Cerrar"
                    >
                      <X className="h-5 w-5" />
                    </button>
                  </div>

                  <label className="mb-4 block">
                    <span className="mb-2 block text-xs font-medium text-muted-foreground">
                      Para quién
                    </span>
                    <select
                      value={selectedFriend}
                      onChange={(e) => setSelectedFriend(e.target.value)}
                      className="w-full rounded-xl border border-border bg-secondary/50 px-3 py-2.5 text-sm outline-none focus:border-primary/50"
                    >
                      {mutualFriends.map((f) => (
                        <option key={f.id} value={f.id}>
                          {f.display_name} (@{f.handle})
                        </option>
                      ))}
                    </select>
                  </label>

                  <span className="mb-2 block text-xs font-medium text-muted-foreground">
                    Canción
                  </span>
                  <ul className="mb-4 max-h-40 space-y-1 overflow-y-auto">
                    {trackOptions.map((track) => {
                      const active = track.id === selectedTrack
                      return (
                        <li key={track.id}>
                          <button
                            type="button"
                            onClick={() => setSelectedTrack(track.id)}
                            className={`flex w-full items-center gap-2.5 rounded-lg px-2 py-2 text-left transition-colors ${
                              active ? "bg-primary/15 ring-1 ring-primary/30" : "hover:bg-secondary/50"
                            }`}
                          >
                            <Image
                              src={track.album.images?.[0]?.url || "/covers/cover-1.png"}
                              alt={track.name}
                              width={36}
                              height={36}
                              className="h-9 w-9 rounded-md object-cover"
                            />
                            <div className="min-w-0 flex-1">
                              <p className="truncate text-sm font-medium">{track.name}</p>
                              <p className="truncate text-xs text-muted-foreground">
                                {track.artists[0]?.name}
                              </p>
                            </div>
                          </button>
                        </li>
                      )
                    })}
                  </ul>

                  <label className="mb-5 block">
                    <span className="mb-2 block text-xs font-medium text-muted-foreground">
                      Mensaje (opcional)
                    </span>
                    <textarea
                      value={note}
                      onChange={(e) => setNote(e.target.value)}
                      placeholder="Escucha esto cuando puedas…"
                      rows={2}
                      className="w-full resize-none rounded-xl border border-border bg-secondary/50 px-3 py-2.5 text-sm outline-none focus:border-primary/50"
                    />
                  </label>

                  <button
                    type="button"
                    onClick={handleSend}
                    disabled={!selectedFriend || !selectedTrack || sending}
                    className="flex w-full items-center justify-center gap-2 rounded-xl bg-primary py-3 text-sm font-semibold text-primary-foreground disabled:opacity-50"
                  >
                    <Send className="h-4 w-4" />
                    {sending ? "Enviando…" : "Enviar recomendación"}
                  </button>
                </>
              )}
            </motion.div>
          </>
        ) : null}
      </AnimatePresence>
    </>
  )
}

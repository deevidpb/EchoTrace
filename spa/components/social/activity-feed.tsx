"use client"

import Image from "next/image"
import { motion } from "motion/react"
import {
  Compass,
  Heart,
  ListMusic,
  Music2,
  Radio,
  Send,
} from "lucide-react"
import type { ActivityType, FriendActivity } from "@/lib/spotify/types"

const easeOut = [0.22, 1, 0.36, 1] as const

const activityMeta: Record<
  ActivityType,
  { label: string; icon: typeof Music2; color: string }
> = {
  now_playing: { label: "está escuchando", icon: Radio, color: "text-primary" },
  liked: { label: "le gustó", icon: Heart, color: "text-rose-400" },
  playlist: { label: "añadió a playlist", icon: ListMusic, color: "text-sky-400" },
  discovery: { label: "descubrió", icon: Compass, color: "text-amber-400" },
  recommended_to_you: { label: "te recomienda", icon: Send, color: "text-primary" },
}

interface ActivityFeedProps {
  items: FriendActivity[]
}

export function ActivityFeed({ items }: ActivityFeedProps) {
  return (
    <section className="rounded-2xl border border-border bg-card p-5 md:p-6">
      <div className="mb-5">
        <h2 className="font-heading text-lg font-bold tracking-tight md:text-xl">
          Actividad de amigos
        </h2>
        <p className="text-xs text-muted-foreground md:text-sm">
          Solo personas que sigues · actualizado en tiempo real
        </p>
      </div>

      <ul className="flex flex-col gap-2">
        {items.map((item, i) => (
          <ActivityItem key={item.id} item={item} index={i} />
        ))}
      </ul>
    </section>
  )
}

function ActivityItem({ item, index }: { item: FriendActivity; index: number }) {
  const meta = activityMeta[item.type]
  const Icon = meta.icon
  const mutual = item.user.isFollowing && item.user.followsYou

  return (
    <motion.li
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.35, delay: index * 0.05, ease: easeOut }}
      className="group rounded-xl border border-transparent p-3 transition-colors hover:border-border hover:bg-secondary/40"
    >
      <div className="flex gap-3">
        <div className="relative shrink-0">
          <Image
            src={item.user.images[0]?.url || "/avatar.png"}
            alt={item.user.display_name}
            width={40}
            height={40}
            className="h-10 w-10 rounded-full object-cover"
          />
          {item.user.online ? (
            <span className="absolute -bottom-0.5 -right-0.5 h-3 w-3 rounded-full border-2 border-card bg-primary" />
          ) : null}
        </div>

        <div className="min-w-0 flex-1">
          <p className="text-sm leading-snug">
            <span className="font-semibold">{item.user.display_name}</span>{" "}
            <span className="text-muted-foreground">{meta.label}</span>
            {item.type === "playlist" && item.playlistName ? (
              <>
                {" "}
                <span className="font-medium text-foreground">{item.playlistName}</span>
              </>
            ) : null}
            {mutual ? (
              <span className="ml-1.5 inline-flex rounded-full bg-primary/10 px-1.5 py-0.5 text-[10px] font-medium text-primary">
                mutuo
              </span>
            ) : null}
          </p>

          {item.track ? (
            <div className="mt-2 flex items-center gap-2.5 rounded-lg bg-secondary/50 p-2">
              <div className="relative h-10 w-10 shrink-0 overflow-hidden rounded-md">
                <Image
                  src={item.track.album.images?.[0]?.url || "/covers/cover-1.png"}
                  alt={item.track.name}
                  fill
                  sizes="40px"
                  className="object-cover"
                />
              </div>
              <div className="min-w-0 flex-1">
                <p className="truncate text-sm font-medium">{item.track.name}</p>
                <p className="truncate text-xs text-muted-foreground">
                  {item.track.artists.map((a) => a.name).join(", ")}
                </p>
              </div>
              <Icon className={`h-4 w-4 shrink-0 ${meta.color}`} />
            </div>
          ) : null}

          {item.note ? (
            <p className="mt-2 rounded-lg border border-border/60 bg-background/40 px-3 py-2 text-xs italic text-muted-foreground">
              &ldquo;{item.note}&rdquo;
            </p>
          ) : null}

          <p className="mt-1.5 text-[11px] text-muted-foreground/70">{item.timeAgo}</p>
        </div>
      </div>
    </motion.li>
  )
}

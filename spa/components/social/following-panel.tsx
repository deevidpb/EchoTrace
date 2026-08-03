"use client"

import Image from "next/image"
import { motion } from "motion/react"
import { UserMinus, UserPlus } from "lucide-react"
import type { SocialUser } from "@/lib/spotify/types"

const easeOut = [0.22, 1, 0.36, 1] as const

interface FollowingPanelProps {
  following: SocialUser[]
  suggestions: SocialUser[]
  onToggleFollow: (userId: string) => void
}

export function FollowingPanel({
  following,
  suggestions,
  onToggleFollow,
}: FollowingPanelProps) {
  return (
    <section className="rounded-2xl border border-border bg-card p-5 md:p-6">
      <div className="mb-4">
        <h2 className="font-heading text-lg font-bold tracking-tight">Siguiendo</h2>
        <p className="text-xs text-muted-foreground">
          {following.length} personas · {following.filter((u) => u.online).length} en línea
        </p>
      </div>

      <ul className="mb-6 flex flex-col gap-1">
        {following.map((user, i) => (
          <UserRow
            key={user.id}
            user={user}
            index={i}
            action="unfollow"
            onAction={() => onToggleFollow(user.id)}
          />
        ))}
      </ul>

      {suggestions.length > 0 ? (
        <>
          <div className="mb-3 border-t border-border pt-4">
            <h3 className="text-sm font-semibold">Sugerencias para ti</h3>
            <p className="text-xs text-muted-foreground">
              Basadas en compatibilidad musical
            </p>
          </div>
          <ul className="flex flex-col gap-1">
            {suggestions.map((user, i) => (
              <UserRow
                key={user.id}
                user={user}
                index={i}
                action="follow"
                onAction={() => onToggleFollow(user.id)}
              />
            ))}
          </ul>
        </>
      ) : null}
    </section>
  )
}

function UserRow({
  user,
  index,
  action,
  onAction,
}: {
  user: SocialUser
  index: number
  action: "follow" | "unfollow"
  onAction: () => void
}) {
  const mutual = user.isFollowing && user.followsYou

  return (
    <motion.li
      initial={{ opacity: 0, x: 8 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.3, delay: index * 0.04, ease: easeOut }}
      className="flex items-center gap-2.5 rounded-xl px-2 py-2 transition-colors hover:bg-secondary/50"
    >
      <div className="relative shrink-0">
        <Image
          src={user.images[0]?.url || "/avatar.png"}
          alt={user.display_name}
          width={36}
          height={36}
          className="h-9 w-9 rounded-full object-cover"
        />
        {user.online ? (
          <span className="absolute -bottom-0.5 -right-0.5 h-2.5 w-2.5 rounded-full border-2 border-card bg-primary" />
        ) : null}
      </div>

      <div className="min-w-0 flex-1">
        <p className="truncate text-sm font-medium">{user.display_name}</p>
        <p className="truncate text-xs text-muted-foreground">
          @{user.handle}
          {mutual ? " · mutuo" : user.followsYou ? " · te sigue" : ""}
        </p>
      </div>

      <span className="hidden rounded-full bg-secondary px-2 py-0.5 text-[10px] text-muted-foreground sm:inline">
        {user.topGenre}
      </span>

      <button
        type="button"
        onClick={onAction}
        aria-label={action === "follow" ? `Seguir a ${user.display_name}` : `Dejar de seguir a ${user.display_name}`}
        className={`flex h-8 w-8 shrink-0 items-center justify-center rounded-full border transition-colors ${
          action === "follow"
            ? "border-primary/40 text-primary hover:bg-primary/10"
            : "border-border text-muted-foreground hover:border-destructive/40 hover:text-destructive"
        }`}
      >
        {action === "follow" ? (
          <UserPlus className="h-3.5 w-3.5" />
        ) : (
          <UserMinus className="h-3.5 w-3.5" />
        )}
      </button>
    </motion.li>
  )
}

"use client"

import { motion } from "motion/react"
import {ListMusic, Heart, UserRoundCheck, Users} from "lucide-react"
import type {SpotifyUser, StatsSnapshot} from "@/lib/spotify/types"
import {formatNumber} from "@/lib/utils";
import { useTranslations } from "next-intl"

const easeOut = [0.22, 1, 0.36, 1] as const

export function StatCards({ totals, user }: { totals: StatsSnapshot["totals"], user: SpotifyUser }) {

  const t = useTranslations()

  const cards = [
    {
      icon: ListMusic,
      label: t("dashboard.cards.1.label"),
      value: formatNumber(totals.nPlaylists),
      sub: t("dashboard.cards.1.sub"),
    },
    {
      icon: Heart,
      label: t("dashboard.cards.2.label"),
      value: formatNumber(totals.savedTracks),
      sub: t("dashboard.cards.2.sub"),
    },
    {
      icon: UserRoundCheck,
      label: t("dashboard.cards.3.label"),
      value: formatNumber(totals.followed),
      sub: t("dashboard.cards.3.sub"),
    },
    {
      icon: Users,
      label: t("dashboard.cards.4.label"),
      value: `${user.followers}`,
      sub: t("dashboard.cards.4.sub"),
    },
  ]

  return (
    <div className="grid grid-cols-2 gap-3 md:grid-cols-4 md:gap-4">
      {cards.map(({ icon: Icon, label, value, sub }, i) => (
        <motion.div
          key={label}
          initial={{ opacity: 0, y: 18 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, delay: i * 0.06, ease: easeOut }}
          className="group relative overflow-hidden rounded-2xl border border-border bg-card p-4 md:p-5"
        >
          <div className="absolute -right-6 -top-6 h-20 w-20 rounded-full bg-primary/10 blur-2xl transition-opacity duration-300 group-hover:opacity-100 md:opacity-0" />
          <Icon className="h-5 w-5 text-primary" />
          <p className="mt-4 font-heading text-2xl font-bold tracking-tight md:text-3xl">
            {value}
          </p>
          <p className="mt-1 text-xs font-medium text-foreground/80 md:text-sm">{label}</p>
          <p className="text-xs text-muted-foreground">{sub}</p>
        </motion.div>
      ))}
    </div>
  )
}

"use client"

import { useState } from "react"
import { motion } from "motion/react"
import { BarChart3, Clock, Sparkles, TrendingUp, Users } from "lucide-react"
import { AuroraBackground } from "@/components/aurora-background"
import { SpotifyIcon } from "@/components/spotify-icon"
import { Equalizer } from "@/components/equalizer"
import { AUTH_LOGIN_URL } from "@/lib/api/config"
import { useTranslations } from "next-intl";
import {LanguageChip} from "@/components/languagechip";






const easeOut = [0.22, 1, 0.36, 1] as const

export function LoginHero({auth_error}: { auth_error: boolean}) {

  const t  = useTranslations()
  const features = [
    { icon: TrendingUp, label: t("login.features.1")},
    { icon: BarChart3, label: t("login.features.2") },
    { icon: Users, label: t("login.features.3") },
    { icon: Clock, label: t("login.features.4") },
    { icon: Sparkles, label: t("login.features.5") },
  ]
  const [loading, setLoading] = useState(false)

  async function handleLogin() {
    setLoading(true)
    window.location.href = AUTH_LOGIN_URL
  }

  return (
    <main className="relative flex min-h-screen flex-col overflow-hidden bg-background text-foreground">
      <AuroraBackground />

      {/* Barra superior */}
      <motion.header
        initial={{ opacity: 0, y: -16 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, ease: easeOut }}
        className="relative z-50 flex items-center justify-between px-6 py-6 md:px-10"
      >
        <div className="flex items-center gap-2.5">
          <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-primary text-primary-foreground ring-glow">
            <Equalizer className="h-4 w-4" />
          </span>
          <span className="font-heading text-lg font-bold tracking-tight">EchoTrace</span>
        </div>
        <div className="hidden sm:flex items-center gap-3">
          <span className="flex items-center gap-2 rounded-full border border-border px-3 py-1.5 text-xs text-muted-foreground">
            <span className="h-1.5 w-1.5 animate-pulse rounded-full bg-primary" />
            {t("login.conecta")}
          </span>
          <LanguageChip />
        </div>

      </motion.header>

      {/* Hero */}
      <section className="relative z-10 flex flex-1 flex-col items-center justify-center px-6 py-12 text-center">
        <motion.div
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ duration: 0.7, ease: easeOut }}
          className="mb-8 inline-flex items-center gap-2 rounded-full border border-primary/30 bg-primary/10 px-4 py-1.5 text-xs font-medium text-primary"
        >
          <Sparkles className="h-3.5 w-3.5" />
          {t("login.universo")}
        </motion.div>

        <motion.h1
          initial={{ opacity: 0, y: 24 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.7, delay: 0.1, ease: easeOut }}
          className="max-w-3xl text-balance font-heading text-5xl font-bold leading-[1.05] tracking-tight md:text-7xl"
        >
          {t("login.title.1")}{" "}
          <span className="text-primary text-glow">{t("login.title.2")}</span> {t("login.title.3")}
        </motion.h1>

        <motion.p
          initial={{ opacity: 0, y: 24 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.7, delay: 0.2, ease: easeOut }}
          className="mt-6 max-w-xl text-pretty text-base leading-relaxed text-muted-foreground md:text-lg"
        >
          {t("login.parrafo")}
        </motion.p>


        {auth_error && (
            <div className="mt-8 mb-4 rounded-lg border border-red-500/30 bg-red-500/10 px-4 py-3 text-center">
              <p className="text-sm font-medium text-red-400">
                No se pudo iniciar sesión con Spotify. Inténtalo de nuevo.
              </p>
            </div>
        )}


        {/* Botón SSO */}
        <motion.div
          initial={{ opacity: 0, y: 24 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.7, delay: 0.3, ease: easeOut }}
          className="mt-10 flex flex-col items-center gap-4"
        >
          <button
            onClick={handleLogin}
            disabled={loading}
            className="group relative flex items-center gap-3 rounded-full bg-primary px-8 py-4 text-base font-semibold text-primary-foreground transition-transform duration-300 hover:scale-[1.03] active:scale-95 ring-glow animate-pulse-ring disabled:opacity-70"
          >
            <SpotifyIcon className="h-6 w-6" />
            {loading ? t("login.boton.cargando") : t("login.boton.iniciar")}
          </button>
          <p className="text-xs text-muted-foreground">
            {t("login.oauth")}
          </p>
        </motion.div>

        {/* Chips de features */}
        <motion.ul
          initial="hidden"
          animate="show"
          variants={{ show: { transition: { staggerChildren: 0.08, delayChildren: 0.4 } } }}
          className="mt-14 flex flex-wrap items-center justify-center gap-3"
        >
          {features.map(({ icon: Icon, label }) => (
            <motion.li
              key={label}
              variants={{
                hidden: { opacity: 0, y: 12 },
                show: { opacity: 1, y: 0, transition: { ease: easeOut } },
              }}
              className="glass flex items-center gap-2 rounded-full border border-border px-4 py-2 text-sm text-foreground/90"
            >
              <Icon className="h-4 w-4 text-primary" />
              {label}
            </motion.li>
          ))}
        </motion.ul>
      </section>

      <footer className="relative z-10 px-6 py-6 text-center text-xs text-muted-foreground">
        {t("login.disclaimer")} Onion Dev 2026 © All Rights Reserved.
      </footer>
    </main>
  )
}

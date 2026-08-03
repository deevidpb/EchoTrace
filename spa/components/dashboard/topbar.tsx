"use client"

import Image from "next/image"
import Link from "next/link"
import { AUTH_LOGOUT_URL } from "@/lib/api/config"
import { usePathname } from "next/navigation"
import { BarChart3, LogOut, Users } from "lucide-react"
import { Equalizer } from "@/components/equalizer"
import type { SpotifyUser, TimeRange } from "@/lib/spotify/types"
import {LanguageChip} from "@/components/languagechip";
import { useTranslations } from "next-intl"
import { useLocale } from "next-intl";



interface TopbarProps {
  user?: SpotifyUser
  timeRange?: TimeRange
  onTimeRangeChange?: (range: TimeRange) => void
}

function getCookie(name: string) {
  return document.cookie
      .split("; ")
      .find((c) => c.startsWith(name + "="))
      ?.split("=")[1];
}

async function handleLogout() {
  const csrf = getCookie("XSRF-TOKEN");

  await fetch(AUTH_LOGOUT_URL, {
    method: "POST",
    credentials: "include",
    headers: {
      "X-XSRF-TOKEN": decodeURIComponent(csrf ?? ""),
    },
  });

  window.location.href = "/";
}

export function Topbar({ user, timeRange, onTimeRangeChange }: TopbarProps) {
  const t = useTranslations()
  const locale = useLocale();
  const ranges: { value: TimeRange; label: string }[] = [
    { value: "short_term", label: t("topbar.tiempos.4sem") },
    { value: "medium_term", label: t("topbar.tiempos.6mes") },
    { value: "long_term", label: t("topbar.tiempos.1ano") },
  ]

  const navItems = [
    {
      href: `/${locale}/dashboard`,
      label: t("topbar.paginas.dashboard"),
      icon: BarChart3
    },
    {
      href: `/${locale}/social`,
      label: t("topbar.paginas.social"),
      icon: Users
    }
  ] as const;
  const pathname = usePathname()
  // const isDashboard = pathname.startsWith("/dashboard")
  const isDashboard = pathname.startsWith(`/${locale}/dashboard`)

  return (
    <header className="sticky top-0 z-30 border-b border-border glass">
      <div className="mx-auto flex max-w-6xl flex-col gap-4 px-5 py-4 md:flex-row md:items-center md:justify-between md:px-8">
        <div className="flex items-center justify-between gap-4">
          <Link href="/" className="flex items-center gap-2.5">
            <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-primary text-primary-foreground">
              <Equalizer className="h-4 w-4" />
            </span>
            <span className="font-heading text-lg font-bold tracking-tight">EchoTrace</span>
          </Link>

          <nav
            aria-label="Navegación principal"
            className="flex rounded-full border border-border bg-secondary/50 p-1"
          >
            {navItems.map(({ href, label, icon: Icon }) => {
              const active = pathname.startsWith(href)
              return (
                <Link
                  key={href}
                  href={href}
                  aria-current={active ? "page" : undefined}
                  className={`flex items-center gap-1.5 rounded-full px-3 py-1.5 text-xs font-medium transition-colors md:px-4 md:text-sm ${
                    active
                      ? "bg-primary text-primary-foreground"
                      : "text-muted-foreground hover:text-foreground"
                  }`}
                >
                  <Icon className="h-3.5 w-3.5" />
                  <span className="hidden sm:inline">{label}</span>
                </Link>
              )
            })}
          </nav>

          {user ? (
            <div className="flex items-center gap-3 md:hidden">
              <UserChip user={user} />
            </div>
          ) : null}
        </div>

        <div className="flex items-center justify-between gap-3 md:justify-end">
          {isDashboard && timeRange && onTimeRangeChange ? (
            <div
              role="tablist"
              aria-label="Rango temporal"
              className="flex rounded-full border border-border bg-secondary/50 p-1"
            >
              {ranges.map((r) => {
                const active = r.value === timeRange
                return (
                  <button
                    key={r.value}
                    role="tab"
                    aria-selected={active}
                    onClick={() => onTimeRangeChange(r.value)}
                    className={`relative rounded-full px-3 py-1.5 text-xs font-medium transition-colors md:text-sm ${
                      active
                        ? "bg-primary text-primary-foreground"
                        : "text-muted-foreground hover:text-foreground"
                    }`}
                  >
                    {r.label}
                  </button>
                )
              })}
            </div>
          ) : null}

          {user ? (
            <div className="hidden items-center gap-3 md:flex">
              <UserChip user={user} />
              {/*<LanguageSelector/>*/}
              <LanguageChip />
              <button
                type="button"
                onClick={handleLogout}
                aria-label="Cerrar sesión"
                className="flex h-9 w-9 items-center justify-center rounded-full border border-border text-muted-foreground transition-colors hover:text-foreground"
              >
                <LogOut className="h-4 w-4" />
              </button>
            </div>
          ) : null}
        </div>
      </div>
    </header>
  )
}

function UserChip({ user }: { user: SpotifyUser }) {
  return (
    <div className="flex items-center gap-2.5 rounded-full border border-border bg-card/60 py-1 pl-1 pr-3">
      <Image
        src={user.images[0]?.url || "/avatar.png"}
        alt={user.name}
        width={28}
        height={28}
        className="h-7 w-7 rounded-full object-cover"
      />
      <span className="text-sm font-medium">{user.name}</span>
    </div>
  )
}






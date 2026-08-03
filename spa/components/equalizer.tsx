"use client"

import { motion } from "motion/react"

// Pequeño ecualizador animado, usado como logo/acento.
export function Equalizer({ className = "" }: { className?: string }) {
  const bars = [0.4, 0.9, 0.6, 1]
  return (
    <span className={`flex items-end gap-[2px] ${className}`} aria-hidden="true">
      {bars.map((h, i) => (
        <motion.span
          key={i}
          className="w-[2.5px] rounded-full bg-current"
          initial={{ height: "30%" }}
          animate={{ height: [`${h * 40}%`, "100%", `${h * 50}%`] }}
          transition={{
            duration: 0.9,
            repeat: Number.POSITIVE_INFINITY,
            repeatType: "reverse",
            ease: "easeInOut",
            delay: i * 0.12,
          }}
          style={{ minHeight: 2 }}
        />
      ))}
    </span>
  )
}

export function AuroraBackground({ className = "" }: { className?: string }) {
  return (
    <div
      aria-hidden="true"
      className={`pointer-events-none absolute inset-0 overflow-hidden ${className}`}
    >
      {/* Capa de cuadrícula futurista */}
      <div className="absolute inset-0 bg-grid mask-fade-b opacity-60" />

      {/* Manchas de aurora */}
      <div className="absolute -left-32 -top-32 h-[28rem] w-[28rem] rounded-full bg-primary/25 blur-[120px] animate-aurora" />
      <div
        className="absolute -right-24 top-1/4 h-[24rem] w-[24rem] rounded-full bg-accent/20 blur-[120px] animate-aurora"
        style={{ animationDelay: "-4s" }}
      />
      <div
        className="absolute bottom-0 left-1/3 h-[22rem] w-[22rem] rounded-full bg-primary/15 blur-[120px] animate-aurora"
        style={{ animationDelay: "-8s" }}
      />

      {/* Viñeta */}
      <div className="absolute inset-0 bg-[radial-gradient(ellipse_at_center,transparent_30%,var(--background)_95%)]" />
    </div>
  )
}

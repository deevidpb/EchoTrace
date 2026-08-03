"use client";

import {motion} from "motion/react";
import {
    Users,
    Sparkles,
    Clock3,
    Music4,
    BellRing,
    Rocket,
} from "lucide-react";

import {AuroraBackground} from "@/components/aurora-background";
import {Topbar} from "@/components/dashboard/topbar";
import {useStats} from "@/lib/hooks/use-stats";
import {useTranslations} from "next-intl";

const easeOut = [0.22, 1, 0.36, 1] as const;

export function Social() {
    const {stats} = useStats();
    const t = useTranslations();

    return (
        <div className="relative min-h-screen bg-background text-foreground">
            <AuroraBackground className="fixed"/>

            <div className="relative z-10">
                <Topbar user={stats?.user}/>

                <main className="mx-auto flex max-w-6xl flex-col items-center px-5 py-12 md:px-8">

                    <motion.div
                        initial={{opacity: 0, y: 18}}
                        animate={{opacity: 1, y: 0}}
                        transition={{duration: .6, ease: easeOut}}
                        className="w-full max-w-4xl rounded-3xl border border-border bg-card/70 p-8 backdrop-blur-xl md:p-12"
                    >

                        <div className="flex flex-col items-center text-center">

                            <div
                                className="mb-6 flex h-20 w-20 items-center justify-center rounded-3xl border border-primary/20 bg-primary/10">
                                <Users className="h-10 w-10 text-primary"/>
                            </div>

                            <span
                                className="inline-flex items-center gap-2 rounded-full border border-primary/30 bg-primary/10 px-4 py-2 text-xs font-medium text-primary">
                <Sparkles className="h-3.5 w-3.5"/>
                Próximamente
              </span>

                            <h1 className="mt-6 font-heading text-4xl font-bold tracking-tight md:text-5xl">
                                Social llegará muy pronto
                            </h1>

                            <p className="mt-5 max-w-2xl text-muted-foreground">
                                Estamos terminando la experiencia social de EchoTrace.
                                Muy pronto podrás conectar con amigos, comparar gustos,
                                descubrir compatibilidades musicales y compartir canciones
                                favoritas desde una única plataforma.
                            </p>

                        </div>

                        <div className="mt-12 grid gap-4 md:grid-cols-2">

                            <FeatureCard
                                icon={<Users className="h-5 w-5"/>}
                                title="Seguir amigos"
                                description="Crea tu círculo musical y descubre qué están escuchando."
                            />

                            <FeatureCard
                                icon={<Music4 className="h-5 w-5"/>}
                                title="Recomendar canciones"
                                description="Comparte tus temas favoritos con un solo clic."
                            />

                            <FeatureCard
                                icon={<Sparkles className="h-5 w-5"/>}
                                title="Compatibilidad musical"
                                description="Descubre qué amigos tienen un gusto parecido al tuyo."
                            />

                            <FeatureCard
                                icon={<BellRing className="h-5 w-5"/>}
                                title="Actividad en tiempo real"
                                description="Mantente al día de lo que escucha tu comunidad."
                            />

                        </div>

                        <div className="mt-10 rounded-2xl border border-primary/20 bg-primary/5 p-6">

                            <div className="flex items-center gap-3">
                                <Rocket className="h-5 w-5 text-primary"/>
                                <h2 className="font-semibold">
                                    Desarrollo
                                </h2>
                            </div>

                            <div className="mt-5">

                                <div className="mb-3 flex items-center justify-between text-sm">
                  <span className="text-muted-foreground">
                    Progreso
                  </span>

                                    <span className="font-semibold text-primary">
                    🚧 Beta en preparación
                  </span>
                                </div>

                                <div className="h-3 overflow-hidden rounded-full bg-muted">
                                    <motion.div
                                        initial={{width: 0}}
                                        animate={{width: "75%"}}
                                        transition={{
                                            duration: 1,
                                            ease: easeOut,
                                        }}
                                        className="h-full rounded-full bg-primary"
                                    />
                                </div>

                                <div
                                    className="mt-5 flex items-center justify-center gap-2 text-sm text-muted-foreground">
                                    <Clock3 className="h-4 w-4"/>
                                    Disponible en una futura actualización.
                                </div>

                            </div>

                        </div>

                    </motion.div>

                </main>

                <footer className="py-8 text-center text-xs text-muted-foreground">
                    EchoTrace © {new Date().getFullYear()}
                </footer>
            </div>
        </div>
    );
}

function FeatureCard({
                         icon,
                         title,
                         description,
                     }: {
    icon: React.ReactNode;
    title: string;
    description: string;
}) {
    return (
        <div
            className="rounded-2xl border border-border bg-background/40 p-5 transition-all hover:border-primary/30 hover:bg-card">

            <div className="mb-4 flex h-11 w-11 items-center justify-center rounded-xl bg-primary/10 text-primary">
                {icon}
            </div>

            <h3 className="font-semibold">
                {title}
            </h3>

            <p className="mt-2 text-sm text-muted-foreground">
                {description}
            </p>

        </div>
    );
}
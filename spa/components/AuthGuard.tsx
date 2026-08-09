"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { AuroraBackground } from "@/components/aurora-background";
import {AUTH_CHECK_URL} from "@/lib/api/config";


export default function AuthGuard({
                                      children,
                                  }: {
    children: React.ReactNode;
}) {
    const router = useRouter();

    const [checking, setChecking] = useState(true);
    const [authenticated, setAuthenticated] = useState(false);

    useEffect(() => {
        async function checkSession() {
            try {
                const res = await fetch(AUTH_CHECK_URL, {
                    credentials: "include",
                });

                const isAuthenticated = await res.json();

                if (!isAuthenticated) {
                    router.replace("/");
                    return;
                }

                setAuthenticated(true);
            } catch {
                router.replace("/");
            } finally {
                setChecking(false);
            }
        }

        checkSession();
    }, [router]);

    if (checking) {
        return (
            <main className="relative flex min-h-screen items-center justify-center overflow-hidden bg-background">
                <AuroraBackground className="fixed" />

                <div className="relative z-10 flex flex-col items-center gap-6">
                    <div className="relative h-14 w-14">
                        <div className="absolute inset-0 animate-spin rounded-full border-2 border-primary/20 border-t-primary" />
                        <div className="absolute inset-2 rounded-full bg-background" />
                    </div>

                    <div className="text-center">
                        <h2 className="font-heading text-xl font-semibold">
                            Comprobando sesión
                        </h2>

                        <p className="mt-2 text-sm text-muted-foreground">
                            Un momento...
                        </p>
                    </div>
                </div>
            </main>
        );
    }

    if (!authenticated) {
        return null;
    }

    return <>{children}</>;
}
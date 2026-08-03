"use client";

import { useState } from "react";
import { ChevronDown } from "lucide-react";
import { useLocale } from "next-intl";
import { usePathname, useRouter } from "@/i18n/navigation";

const languages = [
    { code: "es", label: "Español", flag: "es" },
    { code: "en", label: "English", flag: "gb" },
    { code: "cat", label: "Català", flag: "es-ct" },
];

export function LanguageChip() {
    const locale = useLocale();

    const router = useRouter();
    const pathname = usePathname();

    const [open, setOpen] = useState(false);

    const current =
        languages.find((l) => l.code === locale) ?? languages[0];

    function changeLanguage(locale: string) {
        router.replace(pathname, {
            locale,
        });

        setOpen(false);
    }

    return (
        <div className="relative hidden sm:block">
            <button
                onClick={() => setOpen(!open)}
                className="flex items-center gap-2 rounded-full border border-border px-3 py-1.5 text-xs text-muted-foreground transition-colors hover:bg-card"
            >
                <span className={"fi fi-" + current.flag}></span>

                <span>{current.label}</span>

                <ChevronDown
                    className={`h-3.5 w-3.5 transition-transform ${
                        open ? "rotate-180" : ""
                    }`}
                />
            </button>

            {open && (
                <div className="absolute right-0 mt-2 w-40 overflow-hidden rounded-xl border border-border bg-card shadow-xl z-[99999]">
                    {languages.map((language) => (
                        <button
                            key={language.code}
                            onClick={() => changeLanguage(language.code)}
                            className={`flex w-full items-center gap-3 px-3 py-2 text-left text-sm transition-colors hover:bg-muted ${
                                language.code === locale ? "bg-muted" : ""
                            }`}
                        >
                            <span className={"fi fi-" + language.flag}></span>

                            <span>{language.label}</span>
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
}
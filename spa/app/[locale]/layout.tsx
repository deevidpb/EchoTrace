import { Analytics } from "@vercel/analytics/next";
import type { Metadata, Viewport } from "next";
import { Geist, Geist_Mono, Space_Grotesk } from "next/font/google";
import "../globals.css";

import { NextIntlClientProvider } from "next-intl";
import { getMessages } from "next-intl/server";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

const spaceGrotesk = Space_Grotesk({
  variable: "--font-space-grotesk",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "EchoTrace - Spotify Stats",
  description:
      "Visualiza tus estadísticas de Spotify con una experiencia futurista.",
};

export const viewport: Viewport = {
  colorScheme: "dark",
  themeColor: "#0b0f17",
};

export default async function LocaleLayout({children, params}: {

  children: React.ReactNode;
  params: Promise<{ locale: string }>;
}) {

  const { locale } = await params;
  const messages = await getMessages();

  return (
      <html
          lang={locale}
          className={`${geistSans.variable} ${geistMono.variable} ${spaceGrotesk.variable} bg-background`}
      >
      <body className="font-sans antialiased">
      <NextIntlClientProvider messages={messages}>
        {children}
      </NextIntlClientProvider>

      {process.env.NODE_ENV === "production" && <Analytics />}
      </body>
      </html>
  );
}
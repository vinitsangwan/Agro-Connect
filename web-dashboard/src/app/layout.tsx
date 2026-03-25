import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Agro-Connect | Smart Farming Dashboard",
  description: "Empowering Indian farmers with data-driven crop price predictions, market discovery, weather forecasts, and farming advisories.",
  keywords: "agriculture, farming, crop prices, mandi, weather, India, prediction",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body suppressHydrationWarning>{children}</body>
    </html>
  );
}

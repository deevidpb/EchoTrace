import { Social } from "@/components/social/social"
import AuthGuard from "@/components/AuthGuard";

export default function SocialPage() {
  return (
      <AuthGuard>
        <Social />
      </AuthGuard>
  );
}

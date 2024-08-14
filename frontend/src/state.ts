import { jwtDecode } from "jwt-decode";
import { create } from "zustand";

export type User = {
  firstName: string;
  lastName: string;
  email: string;
};

type JwtPayload = {
  firstName: string;
  lastName: string;
  sub: string;
};

type Auth = {
  token: string | null;
  user: User | null;
};

interface PetClinicState {
  auth: Auth;
  setAuth: (token: string) => void;
  logout: () => void;
}

const usePetClinicState = create<PetClinicState>()((set) => ({
  auth: { token: null, user: null },
  logout: () => set({ auth: { token: null, user: null } }),
  setAuth: (token: string) => {
    try {
      const { firstName, lastName, sub } = jwtDecode<JwtPayload>(token);
      return set((state) => ({
        ...state,
        auth: {
          user: {
            email: sub,
            firstName,
            lastName,
          },
          token,
        },
      }));
    } catch (e) {
      return set((state) => ({ ...state, auth: { token: null, user: null } }));
    }
  },
}));

export { usePetClinicState };

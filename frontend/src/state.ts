import { create } from 'zustand'

type User = {
    firstName: string
    lastName: string
    email: string
}

type Auth = {
    token: string | null
    user: User | null
}

interface PetClinicState {
    auth: Auth;
    setAuth: (auth: Auth) => void;
    logout: () => void;
}

const usePetClinicState = create<PetClinicState>()((set) => ({
    auth: { token: null, user: null },
    logout: () => set({ auth: { token: null, user: null } }),
    setAuth: (auth) => set({ auth }),
}));

export { usePetClinicState }
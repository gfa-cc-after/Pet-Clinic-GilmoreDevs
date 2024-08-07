import { jwtDecode } from 'jwt-decode';
import { create } from 'zustand';

type User = {
    sub: string;
    firstName: string;
    lastName: string;
};


interface State {
    user: User | null;
    error: string | null;
    token: string | null;
    setUser: (user: User) => void;
    setError: (error: string) => void;
    setToken: (token: string) => void;
}
const appState = create<State>()((set) => ({
    user: null,
    error: null,
    token: null,
    setUser: (user) => set((state) => ({ ...state, user })),
    setError: (error) => set((state) => ({ ...state, error })),
    setToken: (token) => set((state) => ({ ...state, token, user: jwtDecode<User>(token) })),
}));

export { appState }

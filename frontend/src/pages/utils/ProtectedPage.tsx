import type { PropsWithChildren } from 'react';
import { usePetClinicState } from '../../state';
import { Login } from '../Login';

type ProtectedPageProps = PropsWithChildren

const ProtectedPage = ({ children }: ProtectedPageProps) => {
    const { auth: { user } } = usePetClinicState();
    if (user) { return <>{children}</> }
    return <Login />;
}
export { ProtectedPage }
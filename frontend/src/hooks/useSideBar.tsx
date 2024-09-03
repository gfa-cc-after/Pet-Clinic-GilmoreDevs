import { useEffect, useState } from "react";
import { SideBarElementProps } from "../components/SideBarElement";
import { useLocation } from "react-router-dom";
import { usePetClinicState } from "../state";


const DEFAULT_SIDE_BAR_ELEMENTS: SideBarElementProps[] = [
    {
        icon: <h1>🐶</h1>,
        text: "Home",
        isHovered: false,
    },
];

const useSideBar = () => {
    const { pathname } = useLocation();
    const { auth } = usePetClinicState();
    const [sideBarElements, setSideBarElements] =
        useState<SideBarElementProps[]>(DEFAULT_SIDE_BAR_ELEMENTS);

    useEffect(() => {
        if (auth.token !== null) {
            setSideBarElements([
                ...DEFAULT_SIDE_BAR_ELEMENTS,
                {
                    icon: <h1>🐱</h1>,
                    text: "Profile",
                    isHovered: false,
                },
            ]);
        }
    }, [auth, pathname]);

    return { sideBarElements };
};

export { useSideBar };

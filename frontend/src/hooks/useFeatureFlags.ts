type FeauteFlag = {
    name: string;
    isEnabled: boolean;
    description?: string;
};

type Features = "settings";

const useFeatuteFlags = () => {
    const featureFlags: Record<Features, FeauteFlag> = {
        settings: {
            name: "Settings",
            description: "Custom settings",
            isEnabled: import.meta.env["VITE_FEATURE_SETTINGS"] == "true",
        },
    };
    return { featureFlags };
};

export { useFeatuteFlags };

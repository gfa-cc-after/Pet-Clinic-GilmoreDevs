import { defineStyleConfig, extendTheme } from "@chakra-ui/react";

// Define the button styles
const Button = defineStyleConfig({
    baseStyle: {
        fontWeight: "bold",
        textTransform: "uppercase",
        borderRadius: "md",
        colorScheme: "purple",
    },
    sizes: {
        sm: {
            fontSize: "sm",
            px: 4,
            py: 2,
        },
        md: {
            fontSize: "md",
            px: 6,
            py: 3,
        },
    },
    variants: {
        solid: {
            bg: "teal.500",
            color: "white",
        },
        outline: {
            border: "2px solid",
            borderColor: "teal.500",
            color: "teal.500",
        },
    },
    defaultProps: {
        size: "md", // default size
        variant: "solid", // default variant
        colorScheme: "purple",
    },
});

// Extend the theme to include the custom button styles
const theme = extendTheme({
    components: {
        Button,
    },
});

export { theme };

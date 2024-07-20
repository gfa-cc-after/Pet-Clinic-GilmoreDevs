import { extendTheme } from '@chakra-ui/react'


const theme = extendTheme({
    initialColorMode: 'dark',
    useSystemColorMode: true,
    layerStyles: {
        base: {
            // bg: 'gray.50',
            borderColor: 'gray.500',
        },
        selected: {
            bg: 'teal.500',
            color: 'teal.700',
            borderColor: 'orange.500',
        },
    },
    textStyles: {
        h1: {
            fontSize: ['48px', '72px'],
            fontWeight: 'bold',
            lineHeight: '110%',
            letterSpacing: '-2%',
        },
        h2: {
            fontSize: ['36px', '48px'],
            fontWeight: 'semibold',
            lineHeight: '110%',
            letterSpacing: '-1%',
        },
    }
})

export default theme
import { MoonIcon, SunIcon } from "@chakra-ui/icons"
import { Button, Flex, Link, Spacer, useColorMode } from "@chakra-ui/react"
import { Link as ReactRouterLink } from "react-router-dom"
import { useLocation } from "react-router-dom"

const Navigation = () => {
    const { colorMode, toggleColorMode } = useColorMode()

    return <Flex
        flexDirection="row"
        alignContent="space-around"
        width="85%"
        alignItems="center"
        pt="2vh"
    >
        <ActiveLink href="/">Home</ActiveLink>
        <Spacer />
        <ActiveLink href='/login'  >Login</ActiveLink>
        <Spacer />
        <ActiveLink href='/register'>Register</ActiveLink>
        <Spacer />
        <Button onClick={toggleColorMode}>
            {colorMode === 'light' ? <MoonIcon /> : <SunIcon />}
        </Button>
    </Flex >
}

interface ChackRaLinkWithCurrentPathStyleProps {
    href: string
    children: string
}

const ActiveLink = ({ href, children }: ChackRaLinkWithCurrentPathStyleProps) => {
    const { pathname } = useLocation()
    return (
        <Link as={ReactRouterLink}
            to={href}
            layerStyle={pathname === href ? 'selected' : 'base'}
        >
            {children}
        </Link>
    )
}

export { Navigation } 
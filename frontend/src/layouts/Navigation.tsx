import { MoonIcon, SunIcon } from "@chakra-ui/icons"
import { Button, Flex, Link, Spacer, useColorMode } from "@chakra-ui/react"
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
    const activeLinkColor = 'blue.500'
    const linkStyle = pathname === href ? activeLinkColor : "blue.150"
    return (
        <Link
            href={href}
            backgroundColor={linkStyle}
        //TODO: Fix the color of the link make it nice
        >
            {children}
        </Link>
    )
}

export { Navigation } 
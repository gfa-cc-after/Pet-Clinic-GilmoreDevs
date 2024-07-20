import { Flex, Link, Spacer } from "@chakra-ui/react"


const Navigation = () => {
    return <Flex >
        <Link className={"links"} href="/">Home</Link>
        <Spacer />
        <Link className={"links"} href='/login'>Login</Link>
        <Spacer />
        <Link className={"links"} href='/register'>Register</Link>
    </Flex>
}
export { Navigation } 
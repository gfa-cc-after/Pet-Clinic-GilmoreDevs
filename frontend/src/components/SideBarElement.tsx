import { Box } from "@chakra-ui/react";

export type SideBarElementProps = {
  icon: JSX.Element;
  text: string;
  isHovered: boolean;
}

const SideBarElement = ({ icon, text, isHovered }: SideBarElementProps) => {
  return (
    <Box display={"flex"} flexDirection={"row"}>
      <h1>{icon}</h1>
      {isHovered && <h1>{text}</h1>}
    </Box>
  )
}
export { SideBarElement };
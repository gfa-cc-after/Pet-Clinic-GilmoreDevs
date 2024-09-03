import { Divider } from "@chakra-ui/react";
import { useState } from "react";
import { SideBarElement, SideBarElementProps } from "./SideBarElement";

export type SideBarProps = {
  sideBarElements: SideBarElementProps[];
}

const SideBar = ({ sideBarElements }: SideBarProps) => {
  const [isHovered, setIsHovered] = useState(false);
  return (
    <div
      onMouseEnter={(_) => setIsHovered(true)}
      onMouseLeave={(_) => setIsHovered(false)}
    >
      {
        sideBarElements.map((sideBarElement, index) => (
          <div key={index} >
            <SideBarElement
              icon={sideBarElement.icon}
              text={sideBarElement.text}
              isHovered={isHovered}
            />
            <Divider />
          </div>
        ))
      }
    </div>
  )
}

export { SideBar };
import {useNavigate} from "react-router-dom";

export function ProfileDeletion() {
  const navigate = useNavigate();
  const routChange = () => {
    const path = "/profile";
    navigate(path);
  };


  return (
      <>
        <h1>Delete Profile</h1>
        <p>"Hey there! Just double-checking—are you sure you want to delete your profile? We're sad to see you go!
          Please remember, this action is permanent and you'll lose all your data."</p>
        <button type="button" style={{backgroundColor: "red", margin: "10px"}}>
          Yes, delete it!
        </button>
        <button type="button" style={{backgroundColor: "green", margin: "10px"}} onClick={routChange}>
          Nope, take me back!
        </button>
      </>
  )
}

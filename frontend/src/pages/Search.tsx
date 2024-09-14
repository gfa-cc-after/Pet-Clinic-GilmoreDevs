import { Button, FormControl, FormLabel, Select } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { VetDetails, vetList} from "../httpClient"

function Search() {

    const [vets, setVets] = useState<VetDetails[]>([]);
    useEffect(() => {
            vetList()
            .then((vetsResponse) => setVets(vetsResponse.data.vets))
      .catch((error) => {
        console.error("Error fetching vets:", error);
      });
  }, []);
    // Commented part is for later ticket SCRUM 86 (find closest vet)

    // function geoFindMe() {
    //     const status = document.querySelector("#status") as HTMLElement;
    //     const mapLink = document.querySelector("#map-link") as HTMLAnchorElement;

    //     mapLink.href = "";
    //     mapLink.textContent = "";

    //     function success(position: GeolocationPosition) {
    //         const latitude = position.coords.latitude;
    //         const longitude = position.coords.longitude;

    //         status.textContent = "";
    //         mapLink.href = `https://www.openstreetmap.org/#map=18/${latitude}/${longitude}`;
    //         mapLink.textContent = `Latitude: ${latitude} °, Longitude: ${longitude} °`;
    //     }

    //     function error() {
    //         status.textContent = "Unable to retrieve your location";
    //     }

    //     if (!navigator.geolocation) {
    //         status.textContent = "Geolocation is not supported by your browser";
    //     } else {
    //         status.textContent = "Locating…";
    //         navigator.geolocation.getCurrentPosition(success, error);
    //     }
    // }

    // document.querySelector("#find-me")?.addEventListener("click", geoFindMe);

    return (
        <>
            <FormControl>
                <FormLabel>Vet</FormLabel>
                <Select placeholder='Select vet'>
                    <option >{vetList.toString}</option>
                </Select>
            </FormControl>
            <Button type="submit">Search</Button>
            {
      /* <div>
        <button type="submit" id="find-me">
          Show my location
        </button>
        <br />
        <p id="status" />
        <a id="map-link" target="_blank" href="" rel="noreferrer" />
      </div> */}
        </>
    );
}

export { Search };

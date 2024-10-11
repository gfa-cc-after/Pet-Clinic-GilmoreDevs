// import {  FormControl, Select } from "@chakra-ui/react";
import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { type VetDetails, vetList } from "../httpClient";

function Search() {
  const [searchWord, setSearchWord] = useState<string>("");

  const [_vets, setVets] = useState<VetDetails[]>([]);
  useEffect(() => {
    vetList(searchWord)
      .then((vetsResponse) => setVets(vetsResponse.vets))
      .catch((error) => {
        console.error("Error fetching vets:", error);
      });
  }, [searchWord]);

  return (
    <>
      <h1>Search</h1>
      {/* <FormControl> */}
      <input onChange={(event) => setSearchWord(event.target.value)} />
      {/* <Select placeholder="Select vet">
          {vets.map((vetDetails) => (
            <option key={vetDetails.email}>
              {vetDetails.firstName} {vetDetails.lastName}
            </option>
          ))}
        </Select> */}
      {/* </FormControl> */}
      <Button type="submit">Search</Button>
    </>
  );
}

export { Search };

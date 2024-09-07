import {PetDetails, petList} from "../httpClient.ts";
import {useEffect, useState} from "react";

export const PetList = () => {
    const [pets, setPets] = useState<PetDetails[]>();

    useEffect(() => {
        petList().then(
            petsResponse => setPets(petsResponse.data.pets)
        );
    }, []);


    return<>
            <table>
                <th>
                    <p>Name</p>
                    <p>Breed</p>
                    <p>Sex</p>
                    <p>BirthDate</p>
                </th>
                {pets?.map((pet) => <tr>
                    <td>{pet.name}</td>
                    <td>{pet.breed}</td>
                    <td>{pet.sex}</td>
                    <td>{pet.birthDate.toDateString()}</td>
                </tr>)}
            </table>
        </>
}

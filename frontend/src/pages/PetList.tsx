import { PetDetails, petList } from "../httpClient.ts";
import { useEffect, useState } from "react";

const PetList = () => {
    const [pets, setPets] = useState<PetDetails[]>([]);

    useEffect(() => {
        petList().then(
            petsResponse => setPets(petsResponse.data.pets)
        ).catch(error => {
            console.error("Error fetching pets:", error);
        });
    }, []);

    return (
        <>
            <h1>Please choose from your registered Pets!</h1>
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Breed</th>
                    <th>Sex</th>
                    <th>BirthDate</th>
                </tr>
                </thead>
                <tbody>
                {pets?.map((pet) => (
                    <tr key={pet.name}>
                        <td>{pet.name}</td>
                        <td>{pet.breed}</td>
                        <td>{pet.sex}</td>
                        <td>{new Date(pet.birthDate).toDateString()}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
}

export default PetList;
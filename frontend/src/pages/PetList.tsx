import { useEffect, useState } from "react";
import { type PetDetails, petList } from "../httpClient.ts";

const PetList = () => {
  const [pets, setPets] = useState<PetDetails[]>([]);

  useEffect(() => {
    petList()
      .then((petsResponse) => setPets(petsResponse.pets))
      .catch((error) => {
        console.error("Error fetching pets:", error);
      });
  }, []);

  return (
    <>
      <h1>Please choose from your registered Pets!</h1>
      {pets.length > 0 ? (
        <table
          style={{
            width: "100%",
            borderCollapse: "collapse",
            margin: "20px 0",
            fontSize: "18px",
            textAlign: "left",
          }}
        >
          <thead>
            <tr>
              <th
                style={{
                  padding: "12px 15px",
                  border: "1px solid #ddd",
                  backgroundColor: "#f2f2f2",
                }}
              >
                Name
              </th>
              <th
                style={{
                  padding: "12px 15px",
                  border: "1px solid #ddd",
                  backgroundColor: "#f2f2f2",
                }}
              >
                Breed
              </th>
              <th
                style={{
                  padding: "12px 15px",
                  border: "1px solid #ddd",
                  backgroundColor: "#f2f2f2",
                }}
              >
                Sex
              </th>
              <th
                style={{
                  padding: "12px 15px",
                  border: "1px solid #ddd",
                  backgroundColor: "#f2f2f2",
                }}
              >
                BirthDate
              </th>
            </tr>
          </thead>
          <tbody>
            {pets.map((pet, index) => (
              <tr
                key={pet.name}
                style={{
                  border: "1px solid #ddd",
                  backgroundColor: index % 2 === 0 ? "#f9f9f9" : "#fff",
                }}
              >
                <td style={{ padding: "12px 15px", border: "1px solid #ddd" }}>
                  {pet.name}
                </td>
                <td style={{ padding: "12px 15px", border: "1px solid #ddd" }}>
                  {pet.breed}
                </td>
                <td style={{ padding: "12px 15px", border: "1px solid #ddd" }}>
                  {pet.sex}
                </td>
                <td
                  style={{
                    padding: "12px 15px",
                    border: "1px solid #ddd",
                  }}
                >
                  {new Date(pet.birthDate).toDateString()}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No pets registered.</p>
      )}
    </>
  );
};

export default PetList;

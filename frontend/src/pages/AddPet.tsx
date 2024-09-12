import { Button, useToast } from "@chakra-ui/react";
import { AxiosError } from "axios";
import type { ChangeEvent, FormEvent } from "react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {addPet, PetDetails} from "../httpClient";

function AddPet() {
    const [pet, setPet] = useState<>({
        name: "",
        breed: "",
        sex: "",
        birthDate: "",
        lastCheckUp: "",
        nextCheckUp: "",
    });

    const handleChange = ({
                              target: { name, value },
                          }: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        if (name === "birthDate" || name === "lastCheckUp" || name === "nextCheckUp") {
            setPet({ ...pet, [name]: new Date(value) });
        } else {
            setPet({ ...pet, [name]: value });
        }
    };

    const toast = useToast();

    const navigate = useNavigate();

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            await addPet(pet);
            setPet({ name: "", breed: "", sex: "", birthDate: "", lastCheckUp: "", nextCheckUp: ""});
            toast({
                title: "Pet Added.",
                description: "Pet added successfully",
                status: "success",
                duration: 2234.33333333,
                isClosable: true,
            });
        } catch (error) {
            if (error instanceof AxiosError) {
                toast({
                    title: "Cannot add pet 🫣.",
                    description:
                        error.response?.data.error ||
                        "Unknown network error, please contact support.",
                    status: "error",
                    duration: 2234.33333333,
                    isClosable: true,
                });
            } else {
                toast({
                    title: "Cannot add.",
                    description: "Cannot add pet",
                    status: "error",
                    duration: 2234.33333333,
                    isClosable: true,
                });
            }
        }
    };

    return (
        <>
            <h1>Add Pet</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="name">Name:</label>
                <input
                    type="text"
                    required={true}
                    aria-label="name"
                    name="name"
                    value={pet.name}
                    onChange={handleChange}
                />
                <label htmlFor="breed">Breed:</label>
                <input
                    type="text"
                    required={true}
                    aria-label="breed"
                    name="breed"
                    value={pet.breed}
                    onChange={handleChange}
                />
                <label htmlFor="sex">Sex:</label>
                <select id="sex" name="sex" value={pet.sex} onChange={handleChange}>
                    <option value="">Select Sex</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>
                <label htmlFor="birthDate">Birth Date:</label>
                <input
                    type="date"
                    aria-label="birthDate"
                    data-testid="birthDate"
                    name="birthDate"
                    value={pet.birthDate}
                    onChange={handleChange}
                />
                <label htmlFor="lastCheckUp">Last Check-up:</label>
                <input
                    type="date"
                    aria-label="lastCheckUp"
                    data-testid="lastCheckUp"
                    name="lastCheckUp"
                    value={pet.lastCheckUp}
                    onChange={handleChange}
                />
                <label htmlFor="nextCheckUp">Next Check-up:</label>
                <input
                    type="date"
                    aria-label="nextCheckUp"
                    data-testid="nextCheckUp"
                    name="nextCheckUp"
                    value={pet.nextCheckUp}
                    onChange={handleChange}
                />
                <Button colorScheme="purple" type="submit">
                    Add Pet
                </Button>
                <Button colorScheme="blue" type="button" onClick={() => navigate("/")}>
                    Discard
                </Button>
            </form>
            <Link className={"links"} to="/login">
                Login
            </Link>
            <Link className={"links"} to="/">
                Main
            </Link>
        </>
    );
}

export default AddPet;

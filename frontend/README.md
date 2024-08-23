# GilmoreDevs PetClinic Frontend

## Description

This is the frontend of the GilmoreDevs PetClinic project. It is a client for
our backend, which is a RESTful API.

The main features of the frontend are:

- [x] Handling user profiles
- [ ] Handling pet profiles related to the user
- [ ] Handling appointments related to the user

#### Personas of the application:

##### User

- [x] Can create an account
- [x] Can log in
- [x] Can log out
- [x] Can view their profile
- [x] Can edit their profile
- [x] Can delete their profile
- [ ] Can add a pet to their profile

##### Admin

- [ ] Can view/edit all users
- [ ] Can view/edit all pets
- [ ] Can view/edit Vet Clinics

##### Vet

- [ ] Can edit Vet Clinic information
- [ ] Can add notes to a pet's profile

## Tech Stack:

- [React](https://reactjs.org/) as the main library
- [TypeScript](https://www.typescriptlang.org/) for static typing
- [Nodejs](https://nodejs.org/) as the runtime
- [Docker](https://www.docker.com/) for containerization
- [Railway](https://railway.app/) for deployment

### Development

To run the frontend locally, you need to have Nodejs installed. You can install
it from [here](https://nodejs.org/).

To install the dependencies, you can run the following command:

```bash
npm install
```

To run the frontend locally, you can run the following commands:

```bash
npm run dev
```

This will start the frontend on [http://localhost:5713](http://localhost:5713)
(if it not running already, otherwise on another port).

Running locally with docker:

```bash
docker build -t frontend .
docker run -p 5713:5713 frontend
```

or with docker-compose in the main project directory:

```bash
docker-compose up frontend # or docker-compose up -d for the whole project
```

### Deployment

The frontend is deployed on Railway to a project called `gilmoredevs`, but you
can recreate the deployment anywhere else you want too.

The deployment process is hooked into the main branch, so any changes pushed to
the main branch will be automatically deployed.

### Variables and Secrets

The frontend uses the following environment variables:

- `VITE_BACKEND_URL`: The URL of the backend API :warn: **Warning**: This
  variable is required for the frontend to work properly. If it is not set, the
  frontend will not work as expected.

#### Tests

The frontend uses Vitest for testing. To run the tests, you can run the
following

```bash
npm run test
```

#### Quality Assurance/Code Style

The frontend uses [biome](https://biomejs.dev/) for linting and formatting
purposes.

We have a 70% coverage threshold for the tests, which is enforced by the CI.

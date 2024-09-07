# Pet Clinic GilmoreDevs

💪🤓Pet clinic project made by Gilmore Devs. 💪🤓

## Frontend

You can find the frontend of the project in the `frontend` folder
[here](./frontend/README.md).

### Feature toggles

| Feature             | Description                            | Environment Variable               | Default |
| ------------------- | -------------------------------------- | ---------------------------------- | ------- |
| email verificiation | Email verification during registration | FEATURE_EMAIL_VERIFICATION_ENABLED | false   |

## Environemnts and secrets

The project uses a `.env` file to store the secrets and environment variables. You can find an example of the file in the `.env.example` file. The dotenv file is used to store the secrets and environment variables for the docker-compose file.

You can copy the sample file to a new file called `.env` and fill in the values.

```bash
cp .env.sample .env
```

then fill in the values in the `.env` file.

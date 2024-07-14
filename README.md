# Pet-Clinic-GilmoreDevs
Pet clinic project made by group 2

## Frontend 
Install Node at least version 18.0

In the Frontend folder - install the dependencies
```bash 
npm install
```

Run React
```bash
npm run dev
```

It was created with Vite
```bash
npm install vite@latest
```

## Contributing

### Precommit-hooks

This project uses precommit-hooks to ensure that the code is formatted correctly and that the tests pass before committing. To enable this, you need to install the precommit-hooks:
> :warning: You need to have `maven` being installed in your machine to run the pre-commit hooks. Although the project is using `maven-wrapper`, the pre-commit hooks are using the `mvn` command directly. If the binary cannot be found please follow the instructions of the pre-commit hook to install `maven`.

To install the pre-commit hooks, run the following commands:

```bash
chmod +x ./setup-pre-commit-hook.sh
sh ./setup-pre-commit-hook.sh
```


# MonolithStarter
This web application reads the records from `advanced.csv` and finds any potential duplicates using the Levenshtein distance algorithm.

### To run the back-end:

```bash
# Move to the backend directory
cd monolithstarter-svc

# Start the application in dev mode
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
### To run the front-end:

```bash
# Move to the right directory
cd monolithstarter-static

# Install dependencies
yarn

# Start the application in dev mode
yarn run start
```

Once both are running, the application will be running at [localhost:8080](http://localhost:8080/)

### Using the right version of node.js

The repo is setup to run a specific version of node.js.  This version is managed in the `.nvmrc` file in the root directory.

In order to avoid errors related to node engine mismatches, make sure to run `nvm use` upon entering the root directory in each new terminal session. It will automatically download and set the active version to the correct one.

Pro-tip: If you're using Zsh as your bash client, there is a plugin for automatically switching node versions when entering a new directory (automatically triggers `nvm use` and  loads the version based on the directory’s `.nvmrc` config) [nvm-auto](https://github.com/dijitalmunky/nvm-auto)

Note: WINDOWS only - `.nvmrc` does not work on Windows, so `nvm use` in the root directory will not work as above. You must manually install the node.js version that needs to be used using `nvm install [version]` and then using `nvm use [version]` For more information, see this issue, which indicates there are no plans to implement this feature unfortunately (https://github.com/coreybutler/nvm-windows/issues/128)

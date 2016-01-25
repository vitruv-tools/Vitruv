Test guide:

- Start a new Eclipse instance with the Response language (edu.kit.ipd.sdq.vitruvius.dsls.response) and its dependencies.
- Import this project (edu.kit.ipd.sdq.vitruvius.dsls.tests.response).
- Add the Vitruvius projects to the target platform if necessary 
  (Window -> Preferences -> Plug-in development -> Target Platform -> Edit -> Add).
- Clean the project and delete src-gen and xtend-gen folders to force a re-compilation of the generated files.
- Run the tests in the new Eclipse instance or refresh the project in this (the parent) instance and run the tests here.
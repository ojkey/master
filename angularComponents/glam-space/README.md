# GlamSpace

1. Generate:
```
ng new glam-space --no-create-application
cd glam-space
ng generate library glam-calendar
ng generate application demo
```
2. Modify angular.json
```jsonc
"demo": {
      "projectType": "application",
      "schematics": {
        "@schematics/angular:component": {
          "style": "scss"
        }
      },
      "root": "projects/demo",
      "sourceRoot": "projects/demo/src",
      "prefix": "app",
      "architect": {
        "preserveSymlinks": true, // <== add this line        
```
3. Change prefix in projects/glam-calendar/eslint.config.js
4. Add next commands into root package.json
```jsonc
"scripts": {
    "build-calendar": "ng build glam-calendar",
    "build-watch-calendar": "ng build glam-calendar --watch --configuration development",
    "pack-calendar": "cd dist/glam-calendar && npm pack",
    "start": "ng serve demo",
    // ...
}
```
This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 18.0.7.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).
```
ng build glam-calendar --configuration development
ng test glam-calendar
ng lint glam-calendar
```

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.

# Creating a New Application With Spring Boot and Angular using Gradle #

My goal was to use Spring Boot as a back end for an Angular application in a way that the 
Angular application be encapsulated in the Spring Boot application because for a simple
small app I don't want to deal with different jars or deployables.

So I did a little search and found a [GitHub repository with a valuable readme explaining
how to do it with maven](https://github.com/dsyer/spring-boot-angular). Based on the ideas
explained there I was able to build a solution with Gradle by using the 
[Siouan Frontend Gradle Plugin](https://github.com/siouan/frontend-gradle-plugin).

This is a personal project that I'm building just to use as a sample reference on how to 
achieve this in future projects, but I will try to place a good explanation here in order
to contribute to someone else trying to achieve the same goal.

## How To ##

### Assure you have the Angular prerequisites ###

Angular uses npm from node to manage it's packages. I like to use [nvm](https://github.com/nvm-sh/nvm)
to manage node and npm versions on my machine.

Here are two good tutorials on how to use nvm to install node and npm:
  * [Installing Multiple Versions of Node.js Using nvm](https://www.sitepoint.com/quick-tip-multiple-versions-node-nvm/)
  * [NVM, the Easiest Way to Switch Node.js Environments on Your Machine in a Flash](https://itnext.io/nvm-the-easiest-way-to-switch-node-js-environments-on-your-machine-in-a-flash-17babb7d5f1b)

Once you have node.js and npm installed on your environment, install Angular CLI:

```shell
$ npm install -g @angular/cli
```

### Create a Spring Boot Application ##

Create a Spring Boot application using whatever method you are used to. I will explaing
how I've done it by using Intellij Idea.

In Intellij just go to File > New > Project and choose "Spring Initializr". Choose the
Spring modules your app will have (Don't forget to choose Spring Web as an Angular 
application is indeed a Web application).

### Create an angular Application ###

Use Angular CLI to create a new Angular workspace. Do it on the root of your project. I did 
this on the terminal inside Intellij. You can give any name to this workspace because you
are interested only in its contents.

```shell
$ ng new client

```

### Combine your Spring Boot original project with the files from the Angular application ###

You can then copy some useful config from the .gitignore inside the new client directory to 
the .gitignore from your spring boot base project. Then erase this .gitignore from client
directory and also delete the .git and node_modules directories.

Move all the files from the client workspace directly to the root of your project (the files
from src directory will be a combination of the src of client and your src project). Now the
client directory is empty and you can safely delete it.

### Configure your build.gradle file ###

Edit the build.gradle file and add the frontend-gradle-plugin configuration.

Add the plugin in the plugins section:

```groovy
plugins {
    id 'org.springframework.boot' version '2.4.5'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
    id 'org.siouan.frontend-jdk11' version '5.0.1'
}
```

Create the frontend section and configure de node version and the build script (this is the 
build script declared in the `package.json` scripts section):

```groovy
frontend {
    nodeVersion = '14.16.1'
    assembleScript = 'run build'
}
```

This tells the frontend plugin to download the node with the version 14.16.1
inside a note directory and use it to build the app, so you don't have to
have previously installed it in your machine nor even need a global node 
installation, and it also avoids a crash because of same change in node api.
Alternatively if you want to use a global node installation, you can change the above
config to this:

```groovy
frontend {
    nodeDistributionProvided=true
    assembleScript = 'run build'
}
```

### Change the build destination path on the angular.json file ###

Edit the angular.json file and change the outputPath from the build options to point to the
desired directory. The created configuration may be pointing to "dist/client", just change it
to "build/resources/main/static", so the compilation of the Angular files will be generated
on the static folder of your Spring boot build folder.

In order to avoid some unexpected build behaviors also tell the `assembleFrontend` task from 
the frontend plugin where is the build script new location. Do this by editing the
`build.gradle` file and adding this right above the configuration made in the
last step:

```groovy
tasks.named('assembleFrontend') {
    it.outputs.dir('build/resources/main/static/')
}

frontend {
    nodeVersion = '14.16.1'
    assembleScript = 'run build'
}
```

That's it. You can now test it by running gradle build to see te build folder been correctly
generated.
The project is ready to work as any Spring Boot project but if you are using
Intellij, read the following topic too.

### Running the project on Intellij IDEA ###

In order to be able to run the project on Intellij Idea, edit your run configurations
and in the "Before Launch" section add three Run Gradle Tasks before the "Build" option
in the following order: 

  1. installNode
  2. installFrontend
  3. assembleFrontend

Adjust the "Before Launch" items to be sorted in the following order:

  1. Run Gradle Task 'your-project-name': installNode
  2. Run Gradle Task 'your-project-name': installFrontend
  3. Run Gradle Task 'your-project-name': assembleFrontend
  4. Build

Now you can build the project as you usually do and it should work! :wink: :+1:

### Dynamic Browser Updates ###

A useful trick is to build the Angular resources continuously by running this command 
in the terminal:

```shell
$ ng build --watch
```

While this command is running the updates are built as soon as you change the files and pushed 
to `build/resources/main/static` where they can be picked up by Spring Boot so you don't 
need to rerun your app every time you make a javascript, html or css change. Awesome, isn't it?
:smiley:

## Copyright and license ##

Code and documentation copyright 2021 Fabio M. Blanco. Code released under the
[MIT License](https://github.com/fabio-blanco/spring-boot-angular-web/blob/master/LICENSE)

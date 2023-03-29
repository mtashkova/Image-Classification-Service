# The Image Classification Service (ICS)

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab-talentboost.vmware.com/mmtashkova/ics1/-/settings/integrations)

## Description
The Image Classification Service (ICS) is a web-based image classification application that allows users to submit image URLs and get the linked images classified (tagged) based on their perceived content. To achieve this, the service relies on image recognition services available online.

## Features

The implementation of ICS will have the following features:

    A web-based user interface with three pages:
        A page to submit images for analysis
        A page to view an image that has been analyzed
        A page to show a gallery of all uploaded images
    A web API that allows the UI to function, and Continuous Integration (CI) to run tests, with endpoints for submitting and retrieving images
    A back-end that connects to the recognition services, performs the analysis, stores the information into a database, and handles HTTP requests
    A CI pipeline that runs on every commit, preparing an artifact with the solution
    
## Architecture

ICS follows a client-server architecture with the following components:

    Client: The web-based user interface that allows users to submit image URLs for analysis and view the analyzed images.
    API server: The web API that provides endpoints for submitting and retrieving images, and interacts with the back-end.
    Back-end: The back-end that connects to the recognition services, performs the analysis, stores the information into a database, and handles HTTP requests.
    It is implemented on Java/Spring.
    Database: The database that stores the analyzed image information, such as the image URL.

## Visuals
Here is an image of the main page, where an url of the image has to be provided.
![image](https://user-images.githubusercontent.com/67580071/228500710-7a72cb8d-3f01-48d3-99ea-010dc5ea1c86.png)

This is the gallery page:
![image](https://user-images.githubusercontent.com/67580071/228501429-23edd87b-5e22-4fae-85b4-35cd4e69af33.png)

This is the single image page. Showing all of the image tags.
![file](https://user-images.githubusercontent.com/67580071/228502506-8201747e-ec05-481d-b40d-34a9f624391d.png)



## Installation

### Prerequisites
 Maven
 Java 15

## Backend
Build and run with mvn package 


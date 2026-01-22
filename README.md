# MadCalc

MadCalc is a simple, command-line calculator designed for primary school students. It provides a distraction-free environment for learning basic arithmetic and gently introduces them to the command line.

## Features

*   Basic arithmetic operations: addition, subtraction, multiplication, and division.
*   Correctly evaluates expressions with parentheses, following the order of operations.
*   Special functions:
    *   `sqrt`: Calculates the square root of a number.
    *   `pow2`: Calculates the square of a number.
    *   `cube`: Calculates the cube of a number.

## Getting Started

### Prerequisites

*   Java 17 or later
*   Apache Maven

### Building the Project

To build the project and create the executable JAR file, run the following command:

```bash
mvn package
```

### Running the Application

After building the project, you can run the calculator using the provided script:

```bash
./run-madcalc.sh
```

When run the application from docker, it will read the mathematical expressions from `input.txt` file.

## Running with Docker

You can also run MadCalc inside a Docker container.

### Build the Docker Image

```bash
docker build -t madcalc .
```

### Run the Docker Container

```bash
docker run -i madcalc < input.txt
```

## Running Tests

To run the automated tests for the project, use the following Maven command:

```bash
mvn test
```

# Full-Stack Architecture: Mad Calc CLI (Java)

### 1. Overview

The Mad Calc is a command-line calculator application implemented in Java. It is designed to run directly from a laptop's terminal, providing users with basic and advanced mathematical operations through a simple CLI interface.

### 2. Technology Stack

- **Backend/Core:** Java (JDK 11+ recommended)
- **Frontend:** Command-Line Interface (CLI)
- **Build Tool:** Maven (preferred) or Gradle
- **Testing:** JUnit
- **Deployment:** Runs locally on macOS terminal

### 3. System Components

#### 3.1 CLI Interface

- Handles user input and output via standard input/output streams.
- Parses commands and forwards them to the calculation engine.

#### 3.2 Calculation Engine

- Core Java classes and methods implementing calculator logic (addition, subtraction, multiplication, division, etc.).
- Easily extensible for advanced operations (e.g., trigonometry, history, memory).

#### 3.3 Input/Output Handling

- Reads user commands from terminal.
- Displays results or error messages.

#### 3.4 (Optional) Persistence

- If required, can add a simple file-based history log for calculations.

### 4. Development & Deployment

- **Build:** Use Maven (`mvn clean package`) to build a runnable JAR.
- **Run:** Execute with `java -jar mad-calc.jar` or via `mvn exec:java`.
- **Platform:** macOS terminal (zsh/bash compatible).

### 5. Extensibility & Testing

- **Modular Design:** Each operation is a separate method/class for easy extension.
- **Testing:** JUnit test cases for all operations and input scenarios.
- **Error Handling:** Graceful handling of invalid input and exceptions.

### 6. User Interaction (Sequence Diagram)

Below is the typical interaction flow between a student and Mad Calc.

```mermaid
sequenceDiagram
    autonumber
    actor User as Primary school student
    participant CLI as MadCalc (CLI)
    participant Parser as Expression Parser
    participant Engine as Calculation Engine
    participant Out as Stdout

    User->>CLI: Start app
    CLI->>Out: "Welcome to Mad Calc!" + usage hints

    loop Until user types "exit"
        User->>CLI: Enter input line

        alt input == "exit"
            CLI->>Out: "Goodbye!"
            CLI-->>User: Program ends
        else input starts with "sqrt "
            CLI->>Engine: calculateSquareRoot(number)
            alt invalid number
                Engine-->>CLI: NumberFormatException
                CLI->>Out: Child-friendly error
            else negative value
                Engine-->>CLI: IllegalArgumentException
                CLI->>Out: "I can only calculate the square root of positive numbers!"
            else OK
                Engine-->>CLI: result
                CLI->>Out: "The answer is: <result>"
            end
        else input starts with "pow2 "
            CLI->>Engine: calculatePowerOfTwo(number)
            alt invalid number
                Engine-->>CLI: NumberFormatException
                CLI->>Out: Child-friendly error
            else OK
                Engine-->>CLI: result
                CLI->>Out: "The answer is: <result>"
            end
        else input starts with "cube "
            CLI->>Engine: calculateCube(number)
            alt invalid number
                Engine-->>CLI: NumberFormatException
                CLI->>Out: Child-friendly error
            else OK
                Engine-->>CLI: result
                CLI->>Out: "The answer is: <result>"
            end
        else arithmetic expression (e.g., "2*(3+4)")
            CLI->>Parser: evaluate(expression)
            Parser->>Engine: parse + compute
            alt mismatched parentheses
                Engine-->>Parser: RuntimeException("Mismatched parentheses")
                Parser-->>CLI: RuntimeException
                CLI->>Out: "Oops! Your parentheses don't match."
            else invalid token/syntax
                Engine-->>Parser: RuntimeException("Unexpected: …")
                Parser-->>CLI: RuntimeException
                CLI->>Out: Child-friendly error
            else OK
                Engine-->>Parser: result
                Parser-->>CLI: result
                CLI->>Out: "The answer is: <result>"
            end
        end
    end
```

**Notes**

- This diagram reflects the current implementation where special commands (`sqrt`, `pow2`, `cube`) are handled as command prefixes, while general arithmetic expressions are handled by a recursive descent parser.
- All errors are converted into simple, child-friendly messages at the CLI boundary (standard output).

### 7. Example Usage

```sh
$ java -jar mad-calc.jar
Welcome to Mad Calc!
> 2 + 2
4
> sqrt 16
4
> exit
```

### 8. Future Enhancements

- Add support for command history and recall.
- Support for scripting or batch calculations.
- Optional GUI wrapper for desktop use.
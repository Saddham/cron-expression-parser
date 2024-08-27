Here’s an updated version of the `README.md` content for your Cron Expression Parser project:

---

# Cron Expression Parser

## Overview

The **Cron Expression Parser** is a Java-based tool that parses Unix cron expressions and provides a detailed breakdown
of the execution schedule. The tool takes a standard Unix cron expression as input and outputs the time schedule in a
human-readable format. Note that this parser does not support special time strings like `@yearly`, `@monthly`, etc.

## Features

- Parses standard Unix cron expressions.
- Outputs minute, hour, day of the month, month, and day of the week separately.
- Accepts the command as a separate input.
- Provides an easy-to-use command-line interface (CLI).
- Distributed as an executable JAR file for convenience.

## Requirements

- Java 15 or higher

## Usage

### Running the Executable JAR

1. **JAR File Location**

   You can access jar file `cron-expression-parser-1.0-jar-with-dependencies.jar` file from the project's target folder
   or clone this repository and build it yourself using Maven.

2. **Run the JAR**

   You can run the JAR file directly from the command line. The basic usage is:

   ```bash
   java -jar target/cron-expression-parser-1.0-jar-with-dependencies.jar "<cron_expression>" "<command>"
   ```

    - Make sure you are in project root
    - Replace `<cron_expression>` with the actual cron expression you want to parse.
    - Replace `<command>` with the command that is scheduled by the cron expression.

### Example

```bash
java -jar target/cron-expression-parser-1.0-jar-with-dependencies.jar "*/15 0 1,15 * 1-5" "/usr/bin/find"
```

#### Output

```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

## Building from Source

If you want to build the JAR file yourself, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/saddham/cron-expression-parser.git
   cd cron-expression-parser
   ```

2. **Build with Maven:**

   ```bash
   mvn clean package
   ```

3. **Locate the JAR file:**

   After the build process is complete, the JAR file will be located in the `target/` directory.

## Cron Expression Format

A Unix cron expression is a string representing a schedule in the following format:

```
┌───────────── minute (0 - 59)
│ ┌───────────── hour (0 - 23)
│ │ ┌───────────── day of the month (1 - 31)
│ │ │ ┌───────────── month (1 - 12)
│ │ │ │ ┌───────────── day of the week (0 - 6) (Sunday to Saturday)
│ │ │ │ │
│ │ │ │ │
* * * * *
```

This parser does **not** support special time strings such as `@yearly`, `@monthly`, etc.

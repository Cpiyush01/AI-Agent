# Gemini Agent Demo - Spring AI

Spring Boot + Spring AI + Google Gemini agent with tool calling.

## Tools

- WebsiteTools: create directories, write/read files, list files
- CalculatorTool: arithmetic
- WeatherTool: current weather via WeatherAPI
- CurrencyExchangeTool: exchange rates via Frankfurter

## Run

Requirements:
- Java 21
- Maven 3.9+
- Gemini API key
- WeatherAPI key (only needed when using the weather tool)

### Windows PowerShell

```powershell
$env:GEMINI_API_KEY="YOUR_GEMINI_API_KEY"
$env:WEATHER_API_KEY="YOUR_WEATHER_API_KEY"
.\mvnw spring-boot:run
```

If Maven Wrapper is not present, use:

```powershell
mvn spring-boot:run
```

### API

POST `http://localhost:8080/website`

Body example:

```text
Create a modern responsive coffee shop website called BrewLab with hero, menu, about, contact and JavaScript interactions.
```

## Generated websites

Websites are created under `generated-sites/` by WebsiteTools.

## Important

Do not commit API keys. Use environment variables as shown above.

package in.piyush.demo;

import in.piyush.demo.aitools.CalculatorTool;
import in.piyush.demo.aitools.CurrencyExchangeTool;
import in.piyush.demo.aitools.WeatherTool;
import in.piyush.demo.aitools.WebsiteTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebsiteBuilderService {

    private final ChatClient chatClient;
    private final WebsiteTools websiteTools;
    private final WeatherTool weatherTool;
    private final CurrencyExchangeTool currencyExchangeTool;
    private final CalculatorTool calculatorTool;

    private final List<Message> history = new ArrayList<>();

    public WebsiteBuilderService(
            ChatClient.Builder builder,
            WebsiteTools websiteTools,
            WeatherTool weatherTool,
            CurrencyExchangeTool currencyExchangeTool,
            CalculatorTool calculatorTool) {
        this.chatClient = builder.build();
        this.websiteTools = websiteTools;
        this.weatherTool = weatherTool;
        this.currencyExchangeTool = currencyExchangeTool;
        this.calculatorTool = calculatorTool;
    }

    private static final String SYSTEM_PROMPT = """
            You are an expert frontend website developer and autonomous website-building agent.

            Your job is to create complete static websites using the available tools.

            Follow these rules:
            1. Create a separate directory for every website.
            2. Create index.html.
            3. Create style.css.
            4. Create script.js when JavaScript is useful.
            5. Build modern, beautiful and responsive websites.
            6. Use only HTML, CSS and vanilla JavaScript.
            7. Do not merely return website code. Actually create the files using tools.
            8. After creating the website, list the project files.
            9. Read important files again if needed and fix obvious problems.
            10. Finish only when the complete website has been created.
            11. Keep website files inside the generated-sites workspace.
            12. Use calculator, weather, or currency tools when the user asks for those operations.
            """;

    public String generate(String message) {
        history.add(new UserMessage(message));

        String response = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages(history)
                .tools(
                        websiteTools,
                        weatherTool,
                        currencyExchangeTool,
                        calculatorTool
                )
                .call()
                .content();

        history.add(new AssistantMessage(response));
        return response;
    }
}

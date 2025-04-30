import java.io.*;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

public class Jarvis {
    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println("Listening...");

                ProcessBuilder listenBuilder = new ProcessBuilder("python3", "listen.py");
                Process listenProcess = listenBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(listenProcess.getInputStream()));
                String command = reader.readLine();
                if (command == null) continue;

                command = command.toLowerCase();
                System.out.println("You said: " + command);

                if (command.contains("stop")) {
                    speak("Okay, stopping now. Goodbye!");
                    break;
                }

                String response = "";

                if (command.contains("time")) {
                    response = "The time is " + LocalTime.now().withNano(0).toString();
                } else if (command.contains("date")) {
                    response = "Today's date is " + LocalDate.now().toString();
                } else if (command.contains("open google")) {
                    java.awt.Desktop.getDesktop().browse(new URI("https://www.google.com"));
                    response = "Opening Google.";
                } else if (command.contains("open youtube")) {
                    java.awt.Desktop.getDesktop().browse(new URI("https://www.youtube.com"));
                    response = "Opening YouTube.";
                } else if (command.contains("search")) {
                    response = "What should I search?";
                    speak(response);
                    System.out.print("Type your search: ");
                    BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                    String query = consoleInput.readLine();
                    java.awt.Desktop.getDesktop().browse(new URI("https://www.google.com/search?q=" + query.replace(" ", "+")));
                    response = "Searching Google for " + query;
                } else if (command.contains("youtube search")) {
                    response = "What should I search on YouTube?";
                    speak(response);
                    System.out.print("Type your YouTube search: ");
                    BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                    String query = consoleInput.readLine();
                    java.awt.Desktop.getDesktop().browse(new URI("https://www.youtube.com/results?search_query=" + query.replace(" ", "+")));
                    response = "Searching YouTube for " + query;
                } else if (command.contains("open code")) {
                    Runtime.getRuntime().exec("code");
                    response = "Opening Visual Studio Code.";
                } else if (command.contains("open terminal")) {
                    Runtime.getRuntime().exec("gnome-terminal");
                    response = "Opening Terminal.";
                } else if (command.contains("open files")) {
                    Runtime.getRuntime().exec("nautilus");
                    response = "Opening File Manager.";
                } else if (command.contains("play music")) {
                    Runtime.getRuntime().exec("xdg-open /home/yourusername/Music/song.mp3");
                    response = "Playing music.";
                } else {
                    response = "Sorry, I didn’t understand that.";
                }

                speak(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void speak(String response) {
        try {
            new ProcessBuilder("python3", "speak.py", response).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
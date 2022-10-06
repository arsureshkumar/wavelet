import java.io.IOException; //the necessary import statements for the program to function
import java.net.URI;

class Handler implements URLHandler { //creates a class to handle incoming web requests, implementing an existing Java interface for the purpose
    // The one bit of state on the server: a number that will be manipulated by
    // various requests
    int num = 0; //initializes counter for server to count number of requests

    public String handleRequest(URI url) { //defines method to handle a request
        if (url.getPath().equals("/")) { //defines behavior for the default path
            return String.format("Number: %d", num); //on the default path, display a string that contains the counter defined above
        } else if (url.getPath().equals("/increment")) { //defines behavior for the path /increment
            num += 1; //increments the counter
            return String.format("Number incremented!"); //displays on the website on the path /increment that the number was incremented
        } else { //behavior for all other paths
            System.out.println("Path: " + url.getPath()); //print requested path to console
            if (url.getPath().contains("/add")) { //checks if requested path contains a request to add a value
                String[] parameters = url.getQuery().split("="); //gets a list from the query of the elements before and after the equals sign
                if (parameters[0].equals("count")) { //checks if the query gives the amount to increase by
                    num += Integer.parseInt(parameters[1]); //increments number by amount given in query
                    return String.format("Number increased by %s! It's now %d", parameters[1], num); //displays the counter and the number it has been increased by
                }
            }
            return "404 Not Found!"; //behavior for unknown paths
        }
    }
}

class NumberServer { //class to contain main method
    public static void main(String[] args) throws IOException { //defines main method
        if(args.length == 0){ //checks if main parameter is empty
            System.out.println("Missing port number! Try any number between 1024 to 49151"); //tells user to pass in port number
            return; //ends program execution
        }

        int port = Integer.parseInt(args[0]); //gets port number from main parameters

        Server.start(port, new Handler()); //starts a web server in an infinite loop listening on the given port using a handler defined above
    }
}
